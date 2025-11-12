package com.example.healthconnect

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class CentrosSalud : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var ubicacionActual: LatLng? = null
    private lateinit var btnBuscar: Button

    private val apiKey = "AIzaSyA3IR7kq4A_GUbCs_pCViqIJ569vtebJpI"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centros_salud)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        btnBuscar = findViewById(R.id.btnBuscarCentros)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Al presionar el botón, buscar centros cercanos
        btnBuscar.setOnClickListener {
            ubicacionActual?.let {
                buscarCentrosSalud(it.latitude, it.longitude)
            } ?: Toast.makeText(this, "Esperando ubicación actual...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        obtenerUbicacionActual()
    }

    private fun obtenerUbicacionActual() {
        // Pedir permisos si no están concedidos
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                ubicacionActual = LatLng(it.latitude, it.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual!!, 15f))
                mMap.addMarker(
                    MarkerOptions()
                        .position(ubicacionActual!!)
                        .title("Tu ubicación actual")
                )
            } ?: run {
                Toast.makeText(this, "No se pudo obtener la ubicación actual", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Nuevo sistema para pedir permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            obtenerUbicacionActual()
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarCentrosSalud(lat: Double, lon: Double) {
        val url =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                    "?location=$lat,$lon" +
                    "&radius=2000" +
                    "&type=hospital" +
                    "&key=$apiKey"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@CentrosSalud,
                        "Error al buscar centros",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) return
                    val json = it.body?.string() ?: return
                    val results = JSONObject(json).optJSONArray("results") ?: return

                    runOnUiThread {
                        mMap.clear()
                        for (i in 0 until results.length()) {
                            val place = results.getJSONObject(i)
                            val loc = place.getJSONObject("geometry").getJSONObject("location")
                            val latPlace = loc.getDouble("lat")
                            val lngPlace = loc.getDouble("lng")
                            val name = place.getString("name")

                            val lugar = LatLng(latPlace, lngPlace)
                            mMap.addMarker(MarkerOptions().position(lugar).title(name))
                        }

                        Toast.makeText(
                            this@CentrosSalud,
                            "Centros cercanos cargados (${results.length()})",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}
