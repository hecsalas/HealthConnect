package com.example.healthconnect

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        //List Views
        val listaServicios: ListView = findViewById(R.id.lista_servicios)
        val listaMedicos: ListView = findViewById(R.id.lista_medicos)

        //Datos
        val servicios = arrayOf(
            "Exámenes disponibles",
            "Historial médico",
            "Mis resultados",
            "Centros de salud cercanos",
            "Configuración"
        )

        val medicos = arrayOf(
            "Dr. Pérez - Cardiología",
            "Dra. Soto - Nutrición",
            "Dr. Gómez - Medicina General",
            "Dra. Ruiz - Pediatría",
            "Dr. Morales - Dermatología",
            "Dra. Fernández - Endocrinología"
        )

        //Adaptadores
        val adaptServicios = ArrayAdapter(this, android.R.layout.simple_list_item_1, servicios)
        val adaptMedicos = ArrayAdapter(this, android.R.layout.simple_list_item_1, medicos)

        listaServicios.adapter = adaptServicios
        listaMedicos.adapter = adaptMedicos

        listaServicios.setOnItemClickListener { parent, view, position, id ->
            val itemElegido = parent.getItemAtPosition(position).toString()

            if (itemElegido == "Historial médico") {
                val nuevaVentana = Intent(this, HistorialMedico::class.java)
                startActivity(nuevaVentana)

            } else if (itemElegido == "Centros de salud cercanos") {
                val nuevaVentana = Intent(this, CentrosSalud::class.java)
                startActivity(nuevaVentana)

            } else if (itemElegido == "Exámenes disponibles") {
                val nuevaVentana = Intent(this, ExamenesDisponibles::class.java)
                startActivity(nuevaVentana)

            } else if (itemElegido == "Configuración") {
                val nuevaVentana = Intent(this, Configuracion::class.java)
                startActivity(nuevaVentana)

            } else if (itemElegido == "Mis resultados") {
                val nuevaVentana = Intent(this, MisResultados::class.java)
                startActivity(nuevaVentana)
            }
        }


        //MEDICOS
        listaMedicos.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetalleDoctor::class.java)
            startActivity(intent)



            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
                )
                insets

            }
        }
    }
}



