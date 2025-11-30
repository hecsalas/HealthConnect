package com.example.healthconnect

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.graphics.drawable.GradientDrawable

class DetalleDoctor : AppCompatActivity() {

    private lateinit var tvNombreDoctor: TextView
    private lateinit var tvEspecialidadDoctor: TextView
    private lateinit var tvDescripcion: TextView

    private val botonesDias = mutableListOf<Button>()
    private val botonesHoras = mutableListOf<Button>()

    private var diaSeleccionado: Button? = null
    private var horaSeleccionada: Button? = null

    private var colorPrimario: Int = 0
    private var colorFondoClaro: Int = 0
    private var colorTextoOscuro: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_doctor)

        colorPrimario = ContextCompat.getColor(this, R.color.primary_selected)
        colorFondoClaro = ContextCompat.getColor(this, R.color.light_gray_background)
        colorTextoOscuro = ContextCompat.getColor(this, R.color.black)

        tvNombreDoctor = findViewById(R.id.tvNombreDoctor)
        tvEspecialidadDoctor = findViewById(R.id.tvEspecialidadDoctor)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        val btnAgendar: Button = findViewById(R.id.btnAgendar)

        cargarDatosDoctor()

        inicializarBotonesDias()
        inicializarBotonesHoras()

        btnAgendar.setOnClickListener {
            if (diaSeleccionado != null && horaSeleccionada != null) {
                val dia = diaSeleccionado!!.text
                val hora = horaSeleccionada!!.text
                val doctor = tvNombreDoctor.text
                Toast.makeText(this, "¡Cita agendada con $doctor el $dia a las $hora!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Por favor, selecciona un día y una hora disponibles.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarDatosDoctor() {
        val intent = intent
        val nombre = intent.getStringExtra("nombre_doctor") ?: "Doctor Desconocido"
        val especialidad = intent.getStringExtra("especialidad_doctor") ?: "Especialidad Desconocida"

        tvNombreDoctor.text = nombre
        tvEspecialidadDoctor.text = especialidad

        tvDescripcion.text = when (especialidad) {
            "Cardiología" -> "Experto en enfermedades del corazón y sistema circulatorio. Certificado con más de 10 años de experiencia."
            "Nutrición" -> "Especialista en planes alimenticios personalizados y dietoterapia para manejo de condiciones crónicas."
            "Medicina General" -> "Médico familiar dedicado al cuidado integral y la prevención en todas las etapas de la vida."
            else -> "Médico dedicado enfocado en la atención integral y el bienestar de sus pacientes."
        }
    }

    private fun inicializarBotonesDias() {
        val contenedorDias: LinearLayout = findViewById(R.id.contenedorDias)
        for (i in 0 until contenedorDias.childCount) {
            val view = contenedorDias.getChildAt(i)
            if (view is Button) {
                botonesDias.add(view)
                setBotonDeseleccionado(view)
                view.setOnClickListener { onDiaClicked(view) }
            }
        }
    }

    private fun inicializarBotonesHoras() {
        val gridHoras: android.widget.GridLayout = findViewById(R.id.gridHoras)
        for (i in 0 until gridHoras.childCount) {
            val view = gridHoras.getChildAt(i)
            if (view is Button) {
                botonesHoras.add(view)
                setBotonDeseleccionado(view)
                view.setOnClickListener { onHoraClicked(view) }
            }
        }
    }

    private fun setBotonDeseleccionado(button: Button) {
        val drawable = ContextCompat.getDrawable(this, R.drawable.bg_rounded_button)?.mutate() as GradientDrawable
        drawable.setColor(colorFondoClaro)
        button.background = drawable
        button.setTextColor(colorTextoOscuro)
    }

    private fun setBotonSeleccionado(button: Button) {
        val drawable = ContextCompat.getDrawable(this, R.drawable.bg_rounded_button)?.mutate() as GradientDrawable
        drawable.setColor(colorPrimario)
        button.background = drawable
        button.setTextColor(ContextCompat.getColor(this, android.R.color.white))
    }

    private fun onDiaClicked(clickedButton: Button) {
        diaSeleccionado?.let { setBotonDeseleccionado(it) }

        setBotonSeleccionado(clickedButton)
        diaSeleccionado = clickedButton
    }

    private fun onHoraClicked(clickedButton: Button) {
        horaSeleccionada?.let { setBotonDeseleccionado(it) }

        setBotonSeleccionado(clickedButton)
        horaSeleccionada = clickedButton
    }
}