package com.example.healthconnect

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExamenesDisponibles : AppCompatActivity() {

    private lateinit var listaExamenes: ListView
    private lateinit var btnAgregar: Button
    private lateinit var adaptador: ArrayAdapter<String>
    private lateinit var examenes: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examenes_disponibles)

        listaExamenes = findViewById(R.id.listaExamenes)
        btnAgregar = findViewById(R.id.btnAgregarExamen)

        // Datos iniciales
        examenes = arrayListOf(
            "Examen de Sangre",
            "Radiografía de Tórax",
            "Examen de Orina",
            "Ecografía Abdominal",
            "Test de Glucosa",
            "Resonancia Magnética"
        )

        // Adaptador
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, examenes)
        listaExamenes.adapter = adaptador

        // AGREGAR examen
        btnAgregar.setOnClickListener { mostrarDialogoAgregar() }

        // EDITAR / ELIMINAR examen con long click
        listaExamenes.setOnItemLongClickListener { _, _, position, _ ->
            mostrarDialogoEditarEliminar(position)
            true
        }

        // Ajuste de pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_examenes_disponibles)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // -------------------------- CRUD --------------------------

    private fun mostrarDialogoAgregar() {
        val input = EditText(this)
        input.hint = "Nombre del examen"

        AlertDialog.Builder(this)
            .setTitle("Agregar Examen")
            .setView(input)
            .setPositiveButton("Agregar") { _, _ ->
                val nuevo = input.text.toString().trim()
                if (nuevo.isNotEmpty()) {
                    examenes.add(nuevo)
                    adaptador.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDialogoEditarEliminar(pos: Int) {
        val opciones = arrayOf("Editar", "Eliminar")

        AlertDialog.Builder(this)
            .setTitle(examenes[pos])
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> mostrarDialogoEditar(pos)
                    1 -> eliminarExamen(pos)
                }
            }
            .show()
    }

    private fun mostrarDialogoEditar(pos: Int) {
        val input = EditText(this)
        input.setText(examenes[pos])

        AlertDialog.Builder(this)
            .setTitle("Editar Examen")
            .setView(input)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevoTexto = input.text.toString().trim()
                if (nuevoTexto.isNotEmpty()) {
                    examenes[pos] = nuevoTexto
                    adaptador.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarExamen(pos: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar examen")
            .setMessage("¿Seguro que deseas eliminar \"${examenes[pos]}\"?")
            .setPositiveButton("Eliminar") { _, _ ->
                examenes.removeAt(pos)
                adaptador.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
