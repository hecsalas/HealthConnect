package com.example.healthconnect

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExamenesDisponibles : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examenes_disponibles)


    val listaExamenes: ListView = findViewById(R.id.listaExamenes)
    val btnAgendar: Button = findViewById(R.id.btnAgendarExamen)

    //Datos
    val examenes = arrayOf(
        "Examen de Sangre",
        "Radiografía de Tórax",
        "Examen de Orina",
        "Ecografía Abdominal",
        "Test de Glucosa",
        "Resonancia Magnética"
    )

        //Adaptadores
        val adaptExamenes = ArrayAdapter(this, android.R.layout.simple_list_item_1, examenes)

        listaExamenes.adapter = adaptExamenes


        btnAgendar.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de agendar próximamente...", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_examenes_disponibles)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
    }
}
}
