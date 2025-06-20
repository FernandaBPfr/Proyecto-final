package com.example.hogwartsquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val casa = intent.getStringExtra("casa") ?: "Gryffindor"

        val imagenCasa = findViewById<ImageView>(R.id.imagenCasa)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)
        val fraseCasa = findViewById<TextView>(R.id.fraseCasa)
        val botonReiniciar = findViewById<Button>(R.id.botonReiniciar)

        textoResultado.text = "¡Tu casa es $casa!"

        when (casa) {
            "Gryffindor" -> {
                imagenCasa.setImageResource(R.drawable.gryffindor)
                fraseCasa.text = "Valentía, osadía y determinación. ¡Bienvenido a Gryffindor!"
            }
            "Ravenclaw" -> {
                imagenCasa.setImageResource(R.drawable.ravenclaw)
                fraseCasa.text = "Inteligencia, creatividad y sabiduría. ¡Eres un verdadero Ravenclaw!"
            }
            "Hufflepuff" -> {
                imagenCasa.setImageResource(R.drawable.hufflepuff)
                fraseCasa.text = "Lealtad, justicia y trabajo duro. ¡Hufflepuff te recibe con los brazos abiertos!"
            }
            "Slytherin" -> {
                imagenCasa.setImageResource(R.drawable.slytherin)
                fraseCasa.text = "Ambición, astucia y liderazgo. ¡Slytherin es tu hogar!"
            }
        }

        botonReiniciar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}