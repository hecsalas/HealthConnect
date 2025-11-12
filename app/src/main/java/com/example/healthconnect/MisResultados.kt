package com.example.healthconnect

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MisResultados : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_resultados)


        val listaResultados: ListView = findViewById(R.id.listResultados)
        val btnActualizarResultados: Button = findViewById(R.id.btnActualizarResultados)

        //DATOS
        val resultados = arrayOf(
            "Hemograma completo - 10/11/2025 - Disponible",
            "Examen de glucosa - 08/11/2025 - En revisión",
            "Radiografía de tórax - 03/11/2025 - Disponible"
        )

        //Adaptadores
        val adaptResultados = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultados)

        listaResultados.adapter = adaptResultados

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