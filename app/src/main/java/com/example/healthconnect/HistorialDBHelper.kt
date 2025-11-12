package com.example.healthconnect

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistorialDBHelper(context: Context)
    : SQLiteOpenHelper(context, "HistorialDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE historial (id INTEGER PRIMARY KEY AUTOINCREMENT, imagen BLOB)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS historial")
        onCreate(db)
    }
}
