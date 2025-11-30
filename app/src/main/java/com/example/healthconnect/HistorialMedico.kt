package com.example.healthconnect

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistorialMedico : AppCompatActivity() {

    private lateinit var layoutHistorial: LinearLayout
    private lateinit var btnAgregarExamen: Button
    private lateinit var btnGlosario: Button  // Nuevo botón

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_medico)

        layoutHistorial = findViewById(R.id.layoutHistorial)
        btnAgregarExamen = findViewById(R.id.btnAgregarExamen)
        btnGlosario = findViewById(R.id.btnGlosario)

        cargarHistorialEjemplo()

        btnAgregarExamen.setOnClickListener { mostrarOpcionesAgregar() }

        // Ir al glosario
        btnGlosario.setOnClickListener {
            startActivity(Intent(this, GlosarioMedico::class.java))
        }
    }

    private fun cargarHistorialEjemplo() {
        agregarExamen("Hemograma Completo", "15 Enero 2024", "Mide valores de glóbulos rojos, blancos y plaquetas.")
        agregarExamen("Radiografía de Tórax", "23 Marzo 2024", "Imagen del tórax para ver pulmones y corazón.")
    }

    private fun agregarExamen(nombre: String, fecha: String, descripcion: String) {
        val card = layoutInflater.inflate(R.layout.item_examen_historial, null)

        val tvNombre = card.findViewById<TextView>(R.id.tvNombreExamen)
        val tvFecha = card.findViewById<TextView>(R.id.tvFechaExamen)

        tvNombre.text = nombre
        tvFecha.text = fecha

        // Hacer clickeable
        card.setOnClickListener {
            val intent = Intent(this, DetalleExamen::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("fecha", fecha)
            intent.putExtra("descripcion", descripcion)
            startActivity(intent)
        }

        layoutHistorial.addView(card)
    }

    private fun mostrarOpcionesAgregar() {
        val opciones = arrayOf("Subir imagen", "Tomar foto", "Subir PDF", "Agregar manualmente")

        AlertDialog.Builder(this)
            .setTitle("Agregar examen")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> { /* Abrir galería */ }
                    1 -> { /* Abrir cámara */ }
                    2 -> { /* Abrir PDF */ }
                    3 -> { /* Nuevo formulario */ }
                }
            }
            .show()
    }
}
