package com.example.healthconnect

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ByteArrayOutputStream

class HistorialMedico : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var btnSubir: Button
    private lateinit var dbHelper: HistorialDBHelper
    private val listaImagenes = mutableListOf<ByteArray>()

    companion object {
        private const val REQUEST_CODE_GALERIA = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial_medico)

        gridView = findViewById(R.id.gridHistorial)
        btnSubir = findViewById(R.id.btnSubirImagen)
        dbHelper = HistorialDBHelper(this)

        cargarImagenesDesdeDB()

        btnSubir.setOnClickListener { abrirGaleria() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_historial)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALERIA)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GALERIA && resultCode == Activity.RESULT_OK && data != null) {
            val uri: Uri? = data.data
            if (uri != null) {
                val inputStream = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream)
                val imagenBytes = stream.toByteArray()

                guardarImagenEnDB(imagenBytes)
                cargarImagenesDesdeDB()
            }
        }
    }

    private fun guardarImagenEnDB(imagen: ByteArray) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("imagen", imagen)
        }
        db.insert("historial", null, values)
        db.close()
    }

    private fun cargarImagenesDesdeDB() {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT imagen FROM historial", null)
        listaImagenes.clear()

        if (cursor.moveToFirst()) {
            do {
                val imagen = cursor.getBlob(0)
                listaImagenes.add(imagen)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        gridView.adapter = object : BaseAdapter() {
            override fun getCount(): Int = listaImagenes.size
            override fun getItem(position: Int): Any = listaImagenes[position]
            override fun getItemId(position: Int): Long = position.toLong()
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup?): android.view.View {
                val imageView = ImageView(this@HistorialMedico)
                val bitmap = BitmapFactory.decodeByteArray(listaImagenes[position], 0, listaImagenes[position].size)
                imageView.setImageBitmap(bitmap)
                imageView.layoutParams = AbsListView.LayoutParams(400, 400)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                return imageView
            }
        }
    }
}
