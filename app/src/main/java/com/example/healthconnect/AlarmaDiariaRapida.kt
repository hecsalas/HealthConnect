package com.example.healthconnect

import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlarmaDiariaRapida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarma_diaria_rapida)

        val btnGuardar = findViewById<Button>(R.id.btnGuardarAlarmaRapida)
        // val timePicker = findViewById<TimePicker>(R.id.timePickerAlarma)

        btnGuardar.setOnClickListener {
            Toast.makeText(this, "Alarma diaria guardada con éxito.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}