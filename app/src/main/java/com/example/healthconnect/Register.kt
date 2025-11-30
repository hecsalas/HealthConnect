package com.example.healthconnect

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnConfirmarRegistro: Button = findViewById(R.id.btnConfirmarRegistro)

        btnConfirmarRegistro.setOnClickListener {
            // Aquí iría la lógica real para guardar el nuevo usuario en una base de datos.

            Toast.makeText(this, "¡Registro exitoso! Por favor, inicia sesión.", Toast.LENGTH_LONG).show()

            // Cerrar la actividad de registro y volver a la pantalla de Login
            finish()
        }
    }
}