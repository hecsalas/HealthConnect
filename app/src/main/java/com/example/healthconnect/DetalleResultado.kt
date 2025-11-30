package com.example.healthconnect

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat

class DetalleResultado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_resultado)

        val nombre = intent.getStringExtra("nombre") ?: "N/A"
        val fecha = intent.getStringExtra("fecha") ?: "N/A"
        val valorStr = intent.getStringExtra("valor") ?: "0"
        val rangoMinStr = intent.getStringExtra("rangoMin") ?: "0"
        val rangoMaxStr = intent.getStringExtra("rangoMax") ?: "1"
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción."

        val valor = valorStr.toFloatOrNull() ?: 0f
        val rangoMin = rangoMinStr.toFloatOrNull() ?: 0f
        val rangoMax = rangoMaxStr.toFloatOrNull() ?: 1f

        findViewById<TextView>(R.id.tvDetalleTitulo).text = nombre
        findViewById<TextView>(R.id.tvDetalleFecha).text = "Fecha: $fecha"
        findViewById<TextView>(R.id.tvValorResultado).text = "Valor: $valorStr"
        findViewById<TextView>(R.id.tvRangoNormal).text = "Rango: $rangoMinStr - $rangoMaxStr"
        findViewById<TextView>(R.id.tvDetalleDescripcion).text = HtmlCompat.fromHtml(descripcion, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val rangoBase = findViewById<RelativeLayout>(R.id.rangoBase)
        val indicadorValor = findViewById<View>(R.id.indicadorValor)

        rangoBase.post {
            val anchoBarra = rangoBase.width.toFloat()
            val rangoTotal = rangoMax - rangoMin

            val rangoVisualMax = rangoMax * 1.2f

            var porcentaje = 0f
            if (valor >= rangoMin) {
                porcentaje = (valor - rangoMin) / (rangoVisualMax - rangoMin)
            } else {
                porcentaje = 0f
            }

            porcentaje = porcentaje.coerceIn(0f, 1f)

            val posicionX = anchoBarra * porcentaje

            val esNormal = (valor >= rangoMin && valor <= rangoMax)
            val colorId = if (esNormal) R.color.success_color else R.color.danger_color

            val indicadorDrawable = ContextCompat.getDrawable(this, R.drawable.bg_indicador_normal) as GradientDrawable
            indicadorDrawable.setColor(ContextCompat.getColor(this, colorId))
            indicadorValor.background = indicadorDrawable

            val layoutParams = indicadorValor.layoutParams as RelativeLayout.LayoutParams

            layoutParams.leftMargin = (posicionX - indicadorValor.width / 2).toInt().coerceIn(0, (anchoBarra - indicadorValor.width).toInt())

            indicadorValor.layoutParams = layoutParams
        }
    }
}