package com.example.hogwartsquiz

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PreguntaActivity : AppCompatActivity() {

    private var musica: MediaPlayer? = null

    data class Pregunta(
        val texto: String,
        val respuestas: List<Respuesta>
    )

    data class Respuesta(
        val imagenResId: Int,
        val casa: String
    )

    private val preguntas = listOf(
        Pregunta(
            texto = "¿Qué valoras más?",
            respuestas = listOf(
                Respuesta(R.drawable.valentia, "Gryffindor"),
                Respuesta(R.drawable.inteligencia, "Ravenclaw"),
                Respuesta(R.drawable.lealtad, "Hufflepuff"),
                Respuesta(R.drawable.ambicion, "Slytherin")
            )
        ),
        Pregunta(
            texto = "¿Qué ambiente prefieres para trabajar o estudiar?",
            respuestas = listOf(
                Respuesta(R.drawable.energia, "Gryffindor"),
                Respuesta(R.drawable.concentracion, "Ravenclaw"),
                Respuesta(R.drawable.aire_libre, "Hufflepuff"),
                Respuesta(R.drawable.silencio, "Slytherin"),
                Respuesta(R.drawable.arte, "Ravenclaw"),
                Respuesta(R.drawable.comodidad, "Hufflepuff")
            )
        ),
        Pregunta(
            texto = "¿Qué harías si pudieras pasar un día como profesor en Hogwarts?",
            respuestas = listOf(
                Respuesta(R.drawable.defensa, "Gryffindor"),
                Respuesta(R.drawable.encantamientos, "Ravenclaw"),
                Respuesta(R.drawable.herbologia, "Hufflepuff"),
                Respuesta(R.drawable.pociones, "Slytherin"),
                Respuesta(R.drawable.criaturas, "Gryffindor"),
                Respuesta(R.drawable.transformaciones, "Ravenclaw")
            )
        ),
        Pregunta(
            texto = "¿Qué tipo de líder eres?",
            respuestas = listOf(
                Respuesta(R.drawable.inspirador, "Gryffindor"),
                Respuesta(R.drawable.intelectual, "Ravenclaw"),
                Respuesta(R.drawable.solidario, "Hufflepuff"),
                Respuesta(R.drawable.visionario, "Slytherin"),
                Respuesta(R.drawable.estrategico, "Slytherin"),
                Respuesta(R.drawable.justo, "Hufflepuff")
            )
        ),
        Pregunta(
            texto = "Si pudieras elegir un objeto mágico, ¿cuál sería?",
            respuestas = listOf(
                Respuesta(R.drawable.espada, "Gryffindor"),
                Respuesta(R.drawable.diadema, "Ravenclaw"),
                Respuesta(R.drawable.copa, "Hufflepuff"),
                Respuesta(R.drawable.collar, "Slytherin")
            )
        ),
        Pregunta(
            texto = "¿Cómo enfrentas los desafíos?",
            respuestas = listOf(
                Respuesta(R.drawable.coraje, "Gryffindor"),
                Respuesta(R.drawable.creatividad, "Ravenclaw"),
                Respuesta(R.drawable.paciencia, "Hufflepuff"),
                Respuesta(R.drawable.habilidades, "Slytherin"),
                Respuesta(R.drawable.aceptando, "Gryffindor"),
                Respuesta(R.drawable.analizando, "Ravenclaw")
            )
        )
    )

    // Para almacenar la selección por pregunta (índice de respuesta o -1 si no hay selección)
    private val seleccionPorPregunta = IntArray(preguntas.size) { -1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregunta)

        mostrarTodasLasPreguntas()

        musica = MediaPlayer.create(this, R.raw.hat)
        musica?.isLooping = true
        musica?.start()

        val botonTerminar = findViewById<Button>(R.id.botonTerminar)
        botonTerminar.setOnClickListener {
            calcularResultadoYMostrar()
        }
    }

    private fun mostrarTodasLasPreguntas() {
        val contenedorPreguntas = findViewById<LinearLayout>(R.id.contenedorPreguntas)
        contenedorPreguntas.removeAllViews()

        for ((indicePregunta, pregunta) in preguntas.withIndex()) {
            // Crear un TextView para la pregunta
            val textoPregunta = TextView(this).apply {
                text = pregunta.texto
                textSize = 18f
                setPadding(0, 24, 0, 8)
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                setTextColor(Color.BLACK)
            }
            contenedorPreguntas.addView(textoPregunta)

            // Crear un GridLayout para respuestas (en pares)
            val esUltimaPregunta = (indicePregunta == preguntas.lastIndex)

            val gridRespuestas = GridLayout(this).apply {
                rowCount = pregunta.respuestas.size
                columnCount = if (esUltimaPregunta) 1 else 2
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }


            for ((indiceRespuesta, respuesta) in pregunta.respuestas.withIndex()) {
                val boton = ImageButton(this).apply {
                    setImageResource(respuesta.imagenResId)
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = if (esUltimaPregunta) LinearLayout.LayoutParams.MATCH_PARENT else 0
                        height = 450
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        setMargins(16, 16, 16, 16)
                    }

                    scaleType = ImageView.ScaleType.CENTER_CROP
                    adjustViewBounds = true
                    setBackgroundColor(0x00000000)
                }


                // Mostrar filtro dorado si está seleccionado
                fun actualizarFiltro() {
                    if (seleccionPorPregunta[indicePregunta] == indiceRespuesta) {
                        boton.setColorFilter(Color.argb(150, 255, 215, 0), PorterDuff.Mode.SRC_ATOP)
                    } else {
                        boton.clearColorFilter()
                    }
                }

                actualizarFiltro()

                boton.setOnClickListener {
                    seleccionPorPregunta[indicePregunta] = indiceRespuesta
                    for (i in 0 until gridRespuestas.childCount) {
                        val btn = gridRespuestas.getChildAt(i) as ImageButton
                        if (i == indiceRespuesta) {
                            btn.setColorFilter(Color.argb(150, 255, 215, 0), PorterDuff.Mode.SRC_ATOP)
                        } else {
                            btn.clearColorFilter()
                        }
                    }
                }

                gridRespuestas.addView(boton)
            }

            contenedorPreguntas.addView(gridRespuestas)
        }
    }


    private fun calcularResultadoYMostrar() {
        val puntajes = mutableMapOf(
            "Gryffindor" to 0,
            "Ravenclaw" to 0,
            "Hufflepuff" to 0,
            "Slytherin" to 0
        )

        for ((indicePregunta, indiceRespuesta) in seleccionPorPregunta.withIndex()) {
            if (indiceRespuesta >= 0) {
                val casa = preguntas[indicePregunta].respuestas[indiceRespuesta].casa
                puntajes[casa] = puntajes[casa]!! + 1
            }
        }

        val casaGanadora = puntajes.maxByOrNull { it.value }?.key ?: "Gryffindor"
        val intent = Intent(this, ResultadoActivity::class.java)
        intent.putExtra("casa", casaGanadora)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        musica?.release()
        musica = null
        super.onDestroy()
    }
}
