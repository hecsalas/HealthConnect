package com.example.healthconnect

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfiguracionAvanzada : AppCompatActivity() {

    private lateinit var listaReglas: ListView
    private lateinit var btnAnadirRegla: Button
    private lateinit var btnGuardarAvanzado: Button
    private lateinit var adaptador: ArrayAdapter<String>
    private lateinit var reglas: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion_avanzada)

        listaReglas = findViewById(R.id.listaReglasAvanzadas)
        btnAnadirRegla = findViewById(R.id.btnAnadirRegla)
        btnGuardarAvanzado = findViewById(R.id.btnGuardarConfiguracionAvanzada)

        // Datos de simulación para reglas avanzadas
        reglas = arrayListOf(
            "Tiroxina: 07:00 AM, Dosis doble Lunes y Jueves",
            "Insulina: Antes de cada comida (Horario variable)",
            "Antibiótico: Cada 8 horas, fin el 28/11"
        )

        // Adaptador para la lista
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, reglas)
        listaReglas.adapter = adaptador

        // Implementación de la función para añadir una nueva regla
        btnAnadirRegla.setOnClickListener {
            mostrarDialogoAgregarRegla()
        }

        // Simulación de guardar
        btnGuardarAvanzado.setOnClickListener {
            Toast.makeText(this, "Configuración avanzada guardada.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun mostrarDialogoAgregarRegla() {
        // Crear un campo de texto para el input
        val input = EditText(this)
        input.hint = "Ej: Vitamina D, 10:00 AM, Días impares"

        // Configurar y mostrar el diálogo
        AlertDialog.Builder(this)
            .setTitle("Añadir Nueva Regla")
            .setView(input)
            .setPositiveButton("Agregar") { _, _ ->
                val nuevaRegla = input.text.toString().trim()
                if (nuevaRegla.isNotEmpty()) {
                    reglas.add(nuevaRegla)
                    // Notificar al adaptador que los datos han cambiado para actualizar la lista
                    adaptador.notifyDataSetChanged()
                    Toast.makeText(this, "Regla añadida.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "La regla no puede estar vacía.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}