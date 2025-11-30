package com.example.healthconnect

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Resultado(
    val nombre: String,
    val fecha: String,
    val estado: String,
    val valor: String,
    val rangoMin: String,
    val rangoMax: String,
    val descripcion: String
)

class MisResultados : AppCompatActivity() {

    private lateinit var listaDatosResultados: List<Resultado>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_resultados)

        val listaResultados: ListView = findViewById(R.id.listResultados)
        val btnActualizarResultados: Button = findViewById(R.id.btnActualizarResultados)

        listaDatosResultados = listOf(
            Resultado(
                "Hemoglobina", "10/11/2025", "Disponible",
                "14.5", "12.0", "15.5",
                "El valor de hemoglobina (14.5 g/dL) está dentro del rango normal (12.0 - 15.5 g/dL), lo que indica una capacidad de transporte de oxígeno adecuada."
            ),
            Resultado(
                "Glucosa en Ayunas", "08/11/2025", "En revisión",
                "110", "70", "100",
                "El nivel de glucosa en ayunas (110 mg/dL) se encuentra ligeramente elevado (rango normal: 70 - 100 mg/dL). Se sugiere repetir la prueba."
            ),
            Resultado(
                "Colesterol LDL", "03/11/2025", "Disponible",
                "140", "0", "130",
                "El colesterol LDL (140 mg/dL) está por encima del límite recomendado (máximo 130 mg/dL). Este valor puede aumentar el riesgo cardiovascular."
            ),
            Resultado(
                "Perfil Lipídico (Triglicéridos)", "20/11/2025", "Disponible",
                "95", "0", "150",
                "El nivel de triglicéridos (95 mg/dL) está dentro del rango óptimo (máximo 150 mg/dL). Mantener estos valores reduce el riesgo de enfermedad arterial."
            ),
            Resultado(
                "Creatinina Sérica", "25/11/2025", "Disponible",
                "1.4", "0.6", "1.2",
                "El nivel de creatinina (1.4 mg/dL) se encuentra ligeramente elevado (rango normal: 0.6 - 1.2 mg/dL), lo que podría indicar una función renal reducida."
            )
        )

        val resultadosVisibles = listaDatosResultados.map {
            "${it.nombre} - ${it.fecha} - ${it.estado}"
        }.toTypedArray()

        val adaptResultados = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultadosVisibles)
        listaResultados.adapter = adaptResultados

        listaResultados.setOnItemClickListener { parent, view, position, id ->
            val resultadoSeleccionado = listaDatosResultados[position]

            val intent = Intent(this, DetalleResultado::class.java).apply {
                putExtra("nombre", resultadoSeleccionado.nombre)
                putExtra("fecha", resultadoSeleccionado.fecha)
                putExtra("valor", resultadoSeleccionado.valor)
                putExtra("rangoMin", resultadoSeleccionado.rangoMin)
                putExtra("rangoMax", resultadoSeleccionado.rangoMax)
                putExtra("descripcion", resultadoSeleccionado.descripcion)
            }
            startActivity(intent)
        }

        btnActualizarResultados.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de actualizar próximamente...", Toast.LENGTH_SHORT).show()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_resultados)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}