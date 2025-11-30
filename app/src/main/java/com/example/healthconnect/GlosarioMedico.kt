package com.example.healthconnect

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class GlosarioMedico : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glosario_medico)

        val lista = findViewById<ListView>(R.id.listaGlosario)

        val glosario = arrayOf(
            "Colesterol Total: Medida total de las grasas (lípidos) presentes en la sangre.",
            "HDL: Lipoproteína de Alta Densidad, conocido como 'colesterol bueno'.",
            "LDL: Lipoproteína de Baja Densidad, conocido como 'colesterol malo'.",
            "Triglicéridos: Tipo de grasa que se almacena en el cuerpo para energía.",
            "Glicemia: Nivel de azúcar (glucosa) en la sangre.",
            "Hemoglobina: Proteína que transporta oxígeno en la sangre.",
            "Leucocitos: Glóbulos blancos, responsables de la defensa del cuerpo.",
            "Plaquetas: Células que ayudan a detener hemorragias y coagulación.",
            "Infiltrado Pulmonar: Acumulación de líquido o células en los pulmones, visible en radiografías.",
            "Silueta Cardíaca: Imagen del corazón vista en una radiografía de tórax.",
            "Ecografía: Estudio con ondas de sonido para ver órganos internos.",
            "Linfocitos: Tipo de glóbulos blancos relacionados con el sistema inmune."
        )

        val glosarioOrdenado = glosario.sortedArray()

        lista.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, glosarioOrdenado)
    }
}