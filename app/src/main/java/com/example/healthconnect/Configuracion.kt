package com.example.healthconnect

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class Configuracion : AppCompatActivity() {

    private lateinit var switchNotificaciones: Switch
    private lateinit var switchModoOscuro: Switch
    private lateinit var btnEditarPerfil: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        // Inicializar vistas
        switchNotificaciones = findViewById(R.id.switchNotificaciones)
        switchModoOscuro = findViewById(R.id.switchModoOscuro)
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        // Guardar preferencias del usuario
        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Cargar estados guardados
        switchNotificaciones.isChecked = prefs.getBoolean("notificaciones", true)
        switchModoOscuro.isChecked = prefs.getBoolean("modo_oscuro", false)

        // Listener Notificaciones
        switchNotificaciones.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notificaciones", isChecked).apply()
            Toast.makeText(
                this,
                if (isChecked) "Notificaciones activadas" else "Notificaciones desactivadas",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Listener Modo Oscuro
        switchModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("modo_oscuro", isChecked).apply()
            if (isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //  Redirige a Editar Perfil
        btnEditarPerfil.setOnClickListener {
            val intent = Intent(this, EditarPerfil::class.java)
            startActivity(intent)
        }

        //  Cerrar sesión
        btnCerrarSesion.setOnClickListener {
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()

            prefs.edit().clear().apply()

            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            finish()
        }
    }
}
