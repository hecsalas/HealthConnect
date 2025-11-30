package com.example.healthconnect

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.util.*

class EditarPerfil : AppCompatActivity() {

    private lateinit var imgPerfil: ImageView
    private lateinit var btnCambiarFoto: Button
    private lateinit var edtNombre: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtTelefono: EditText
    private lateinit var edtDireccion: EditText
    private lateinit var edtFechaNacimiento: EditText
    private lateinit var btnGuardar: Button

    private lateinit var prefs: SharedPreferences
    private val PICK_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        // Inicializar vistas
        imgPerfil = findViewById(R.id.imgPerfil)
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto)
        edtNombre = findViewById(R.id.edtNombre)
        edtEmail = findViewById(R.id.edtEmail)
        edtTelefono = findViewById(R.id.edtTelefono)
        edtDireccion = findViewById(R.id.edtDireccion)
        edtFechaNacimiento = findViewById(R.id.edtFechaNacimiento)
        btnGuardar = findViewById(R.id.btnGuardarCambios)

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Cargar datos guardados
        cargarDatos()

        // Fecha de nacimiento
        edtFechaNacimiento.setOnClickListener {
            abrirDatePicker()
        }

        // Cambiar foto
        btnCambiarFoto.setOnClickListener {
            abrirGaleria()
        }

        // Guardar cambios
        btnGuardar.setOnClickListener {
            guardarDatos()
        }
    }

    // ---------------------------------------------------------
    // CARGAR DATOS DESDE SharedPreferences
    // ---------------------------------------------------------
    private fun cargarDatos() {
        edtNombre.setText(prefs.getString("nombre", ""))
        edtEmail.setText(prefs.getString("email", ""))
        edtTelefono.setText(prefs.getString("telefono", ""))
        edtDireccion.setText(prefs.getString("direccion", ""))
        edtFechaNacimiento.setText(prefs.getString("fecha_nac", ""))

        // Cargar foto (si existe)
        val base64 = prefs.getString("foto", null)
        if (base64 != null) {
            val bytes = Base64.decode(base64, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imgPerfil.setImageBitmap(bitmap)
        }
    }

    // ---------------------------------------------------------
    // GUARDAR DATOS
    // ---------------------------------------------------------
    private fun guardarDatos() {
        prefs.edit()
            .putString("nombre", edtNombre.text.toString())
            .putString("email", edtEmail.text.toString())
            .putString("telefono", edtTelefono.text.toString())
            .putString("direccion", edtDireccion.text.toString())
            .putString("fecha_nac", edtFechaNacimiento.text.toString())
            .apply()

        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
        finish()
    }

    // ---------------------------------------------------------
    // DATE PICKER
    // ---------------------------------------------------------
    private fun abrirDatePicker() {
        val c = Calendar.getInstance()
        val año = c.get(Calendar.YEAR)
        val mes = c.get(Calendar.MONTH)
        val dia = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                edtFechaNacimiento.setText("$dayOfMonth/${month + 1}/$year")
            },
            año, mes, dia
        )

        datePicker.show()
    }

    // ---------------------------------------------------------
    // ABRIR GALERÍA
    // ---------------------------------------------------------
    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    // ---------------------------------------------------------
    // RECIBIR FOTO SELECCIONADA
    // ---------------------------------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            val imageUri: Uri? = data?.data
            if (imageUri != null) {
                imgPerfil.setImageURI(imageUri)

                // Convertir a Base64 para guardar
                val bitmap = uriToBitmap(imageUri)
                guardarFoto(bitmap)
            }
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        val input = contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(input!!)
    }

    private fun guardarFoto(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bytes = stream.toByteArray()
        val base64 = Base64.encodeToString(bytes, Base64.DEFAULT)

        prefs.edit().putString("foto", base64).apply()
    }
}
