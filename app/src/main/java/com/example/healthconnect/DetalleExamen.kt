package com.example.healthconnect

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class DetalleExamen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_examen)

        val tvTitulo = findViewById<TextView>(R.id.tvTituloDetalle)
        val tvFecha = findViewById<TextView>(R.id.tvFechaDetalle)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcionDetalle)

        val nombre = intent.getStringExtra("nombre")
        val fecha = intent.getStringExtra("fecha")
        val descripcion = intent.getStringExtra("descripcion")

        tvTitulo.text = nombre
        tvFecha.text = fecha

        if (descripcion != null) {
            val htmlFormattedDescription = descripcion
                .replace("**", "<b>") // Inicio de negrita
                .replace("</b>", "</b>") // Fin de negrita

            tvDescripcion.text = HtmlCompat.fromHtml(
                htmlFormattedDescription,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }
}