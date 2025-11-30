package com.example.healthconnect

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExamenDBHelper(context: Context) :
    SQLiteOpenHelper(context, "examenes.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
            CREATE TABLE examenes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                descripcion TEXT NOT NULL
            )
        """
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS examenes")
        onCreate(db)
    }

    // CREATE
    fun insertarExamen(nombre: String, descripcion: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
        }
        return db.insert("examenes", null, values) > 0
    }

    // READ
    fun obtenerExamenes(): MutableList<Examen> {
        val lista = mutableListOf<Examen>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM examenes", null)

        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    Examen(
                        id = cursor.getInt(0),
                        nombre = cursor.getString(1),
                        descripcion = cursor.getString(2)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }

    // UPDATE
    fun actualizarExamen(id: Int, nombre: String, descripcion: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
        }
        return db.update("examenes", values, "id=?", arrayOf(id.toString())) > 0
    }

    // DELETE
    fun eliminarExamen(id: Int): Boolean {
        val db = writableDatabase
        return db.delete("examenes", "id=?", arrayOf(id.toString())) > 0
    }
}
