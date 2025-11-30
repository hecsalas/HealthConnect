package com.example.healthconnect

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HistorialMedico : AppCompatActivity() {

    private lateinit var layoutHistorial: LinearLayout
    private lateinit var btnAgregarExamen: Button
    private lateinit var btnGlosario: Button

    private val PDF_REQUEST_CODE = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_medico)

        layoutHistorial = findViewById(R.id.layoutHistorial)
        btnAgregarExamen = findViewById(R.id.btnAgregarExamen)
        btnGlosario = findViewById(R.id.btnGlosario)

        cargarHistorialEjemplo()

        btnAgregarExamen.setOnClickListener { mostrarOpcionesAgregar() }

        btnGlosario.setOnClickListener {
            startActivity(Intent(this, GlosarioMedico::class.java))
        }
    }

    private fun cargarHistorialEjemplo() {
        agregarExamen(
            nombre = "Hemograma Completo",
            fecha = "15 Enero 2024",
            descripcion = """
                **Resultados Clave:**
                - Hemoglobina: 14.5 g/dL (Rango: 12.0 - 15.5)
                - Leucocitos: 7.2 x 10^3/µL (Rango: 4.5 - 11.0)
                - Plaquetas: 250 x 10^3/µL (Rango: 150 - 450)
                
                **Observación:** Valores dentro de los rangos normales.
            """.trimIndent()
        )

        agregarExamen(
            nombre = "Radiografía de Tórax",
            fecha = "23 Marzo 2024",
            descripcion = """
                **Hallazgos:**
                - Campos pulmonares sin infiltrados ni consolidaciones.
                - Silueta cardíaca de tamaño y contornos normales.
                - Senos costofrénicos libres.
                
                **Conclusión:** Estudio normal.
            """.trimIndent()
        )

        agregarExamen(
            nombre = "Perfil Lipídico",
            fecha = "10 Abril 2024",
            descripcion = """
                **Resultados Clave:**
                - Colesterol Total: 215 mg/dL (Alto - Rango < 200)
                - HDL: 45 mg/dL (Rango > 40)
                - LDL: 135 mg/dL (Alto - Rango < 100)
                - Triglicéridos: 160 mg/dL (Límite Alto - Rango < 150)
                
                **Observación:** Se recomienda control dietético.
            """.trimIndent()
        )

        agregarExamen(
            nombre = "Glicemia en Ayunas",
            fecha = "05 Septiembre 2024",
            descripcion = """
                **Resultado:**
                - Glucosa: 95 mg/dL (Rango: 70 - 100)
                
                **Observación:** Niveles de glucosa normales.
            """.trimIndent()
        )
    }

    private fun agregarExamen(nombre: String, fecha: String, descripcion: String) {
        val card = layoutInflater.inflate(R.layout.item_examen_historial, null)

        val tvNombre = card.findViewById<TextView>(R.id.tvNombreExamen)
        val tvFecha = card.findViewById<TextView>(R.id.tvFechaExamen)

        tvNombre.text = nombre
        tvFecha.text = fecha

        card.setOnClickListener {
            val intent = Intent(this, DetalleExamen::class.java)
            intent.putExtra("nombre", tvNombre.text.toString())
            intent.putExtra("fecha", fecha)
            intent.putExtra("descripcion", descripcion)
            startActivity(intent)
        }

        card.setOnLongClickListener {
            mostrarOpcionesEdicion(tvNombre.text.toString(), card)
            true
        }

        layoutHistorial.addView(card)
    }

    private fun mostrarOpcionesAgregar() {
        val opciones = arrayOf("Ingresar Examen Manualmente", "Subir PDF")

        AlertDialog.Builder(this)
            .setTitle("Agregar examen")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> mostrarDialogoIngresoManual() // Opción 1: Ingreso Manual
                    1 -> abrirSelectorPDF()            // Opción 2: Subir PDF
                }
            }
            .show()
    }

    private fun mostrarDialogoIngresoManual() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 20, 50, 20)

        val inputNombre = EditText(this)
        inputNombre.hint = "Nombre del Examen (Ej: Presión Arterial)"
        layout.addView(inputNombre)

        val inputFecha = EditText(this)
        inputFecha.hint = "Fecha (Ej: 20 Noviembre 2024)"
        layout.addView(inputFecha)

        AlertDialog.Builder(this)
            .setTitle("Ingreso Manual de Examen")
            .setView(layout)
            .setPositiveButton("Guardar") { dialog, _ ->
                val nombre = inputNombre.text.toString().trim()
                val fecha = inputFecha.text.toString().trim()

                if (nombre.isNotBlank() && fecha.isNotBlank()) {
                    agregarExamen(
                        nombre = nombre,
                        fecha = fecha,
                        descripcion = "**Datos Ingresados Manualmente:** (Detalles no disponibles)"
                    )
                    Toast.makeText(this, "Examen '$nombre' agregado.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Debe ingresar nombre y fecha.", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun abrirSelectorPDF() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(
                Intent.createChooser(intent, "Seleccionar PDF de examen"),
                PDF_REQUEST_CODE
            )
        } catch (e: Exception) {
            Toast.makeText(this, "No se encontró un explorador de archivos compatible.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val pdfUri = data?.data

            if (pdfUri != null) {
                Toast.makeText(this, "PDF seleccionado con éxito", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PDF_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Selección de PDF cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarOpcionesEdicion(nombreExamen: String, cardView: View) {
        val opciones = arrayOf("Editar nombre", "Eliminar")

        AlertDialog.Builder(this)
            .setTitle("Opciones para: $nombreExamen")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> mostrarDialogoEdicion(nombreExamen, cardView)
                    1 -> mostrarDialogoConfirmacionEliminar(nombreExamen, cardView)
                }
            }
            .show()
    }

    private fun mostrarDialogoEdicion(nombreActual: String, cardView: View) {
        val input = EditText(this)
        input.setText(nombreActual)

        AlertDialog.Builder(this)
            .setTitle("Editar Nombre del Examen")
            .setView(input)
            .setPositiveButton("Guardar") { dialog, _ ->
                val nuevoNombre = input.text.toString().trim()
                if (nuevoNombre.isNotBlank()) {
                    val tvNombre = cardView.findViewById<TextView>(R.id.tvNombreExamen)
                    tvNombre.text = nuevoNombre
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun mostrarDialogoConfirmacionEliminar(nombreExamen: String, cardView: View) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Eliminación")
            .setMessage("¿Estás seguro que deseas eliminar el examen '$nombreExamen'? Esta eliminación es solo visual.")
            .setPositiveButton("Eliminar") { dialog, id ->
                layoutHistorial.removeView(cardView)
            }
            .setNegativeButton("Cancelar") { dialog, id ->
                dialog.dismiss()
            }
            .show()
    }
}