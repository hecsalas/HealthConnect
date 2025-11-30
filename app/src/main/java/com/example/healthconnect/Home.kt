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

        // List Views
        val listaServicios: ListView = findViewById(R.id.lista_servicios)
        val listaMedicos: ListView = findViewById(R.id.lista_medicos)

        // Datos
        val servicios = arrayOf(
            "Recordatorios",
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

        // Adaptadores
        // Adaptador para Servicios (usa list_item_card)
        val adaptServicios = ArrayAdapter(
            this,
            R.layout.list_item_card,
            R.id.tv_item_text,
            servicios
        )

        // Adaptador para Médicos: AHORA TAMBIÉN USA list_item_card
        val adaptMedicos = ArrayAdapter(
            this,
            R.layout.list_item_card, // <--- CAMBIO AQUÍ
            R.id.tv_item_text,      // <--- CAMBIO AQUÍ
            medicos
        )

        listaServicios.adapter = adaptServicios
        listaMedicos.adapter = adaptMedicos

        // 3. Lógica de Navegación de Servicios
        listaServicios.setOnItemClickListener { parent, view, position, id ->
            val itemElegido = parent.getItemAtPosition(position).toString()

            val intentDestino = when (itemElegido) {
                "Historial médico" -> Intent(this, HistorialMedico::class.java)
                "Centros de salud cercanos" -> Intent(this, CentrosSalud::class.java)
                "Recordatorios" -> Intent(this, SistemaRecordatorios::class.java)
                "Configuración" -> Intent(this, Configuracion::class.java)
                "Mis resultados" -> Intent(this, MisResultados::class.java)
                else -> null
            }

            intentDestino?.let { startActivity(it) }
        }

        listaMedicos.setOnItemClickListener { parent, view, position, id ->
            val medicoCompleto = parent.getItemAtPosition(position).toString() // Ej: "Dr. Pérez - Cardiología"

            // Separar nombre y especialidad
            val partes = medicoCompleto.split(" - ")
            val nombre = partes[0]
            val especialidad = partes[1]

            val intent = Intent(this, DetalleDoctor::class.java)

            // *** AÑADIR ESTAS LÍNEAS PARA ENVIAR DATOS ***
            intent.putExtra("nombre_doctor", nombre)
            intent.putExtra("especialidad_doctor", especialidad)

            startActivity(intent)
        }

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