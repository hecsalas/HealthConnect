package com.example.healthconnect

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class GlosarioMedico : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glosario_medico)

        val lista = findViewById<ListView>(R.id.listaGlosario)

        val glosario = arrayOf(
            "Hemoglobina: Proteína que transporta oxígeno en la sangre.",
            "Glucemia: Nivel de azúcar en la sangre.",
            "Plaquetas: Células que ayudan a detener hemorragias.",
            "Ecografía: Estudio con ondas de sonido para ver órganos internos.",
            "Linfocitos: Glóbulos blancos relacionados con defensas del cuerpo."
        )

        lista.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, glosario)
    }
}
