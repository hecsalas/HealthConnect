package com.example.healthconnect

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetalleDoctor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_doctor)

        val btnAgendar: Button = findViewById(R.id.btnAgendar)
        val chkConfirmar: CheckBox = findViewById(R.id.chkConfirmar)

        btnAgendar.setOnClickListener {
            if (chkConfirmar.isChecked) {
                Toast.makeText(this, "Cita agendada con éxito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Debes confirmar la selección antes de agendar", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_detalle_doctor)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
