package com.example.healthconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SistemaRecordatorios : AppCompatActivity() {

    private lateinit var btnAlarmaDiaria: Button
    private lateinit var btnOpcionesAvanzadas: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sistema_recordatorios)

        btnAlarmaDiaria = findViewById(R.id.btnAlarmaDiaria)
        btnOpcionesAvanzadas = findViewById(R.id.btnOpcionesAvanzadas)

        btnAlarmaDiaria.setOnClickListener {
            startActivity(Intent(this, AlarmaDiariaRapida::class.java))
        }

        btnOpcionesAvanzadas.setOnClickListener {
            startActivity(Intent(this, ConfiguracionAvanzada::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_sistema_recordatorios)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}