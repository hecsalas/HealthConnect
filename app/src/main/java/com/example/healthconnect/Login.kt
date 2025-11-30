package com.example.healthconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val etUsuario: EditText = findViewById(R.id.etUsuario)
        val edtPassword: EditText = findViewById(R.id.edtPassword)
        val txMensaje: TextView = findViewById(R.id.txMensaje)

        // Variables para login
        val usuarioBase = "admin"
        val passwdBase = "admin"

        btnLogin.setOnClickListener {

            if (etUsuario.text.toString() == usuarioBase &&
                edtPassword.text.toString() == passwdBase) {

                val nuevaVentana = Intent(this, Home::class.java)
                startActivity(nuevaVentana)

                val sesUsername = etUsuario.text.toString()
                Toast.makeText(this, "Bienvenid@s: $sesUsername", Toast.LENGTH_SHORT).show()

                txMensaje.text = "Login Correcto!!"

            } else {
                txMensaje.text = "Login Fallido!!"
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
