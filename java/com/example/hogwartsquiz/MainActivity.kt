package com.example.hogwartsquiz

import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.media.MediaPlayer
import android.widget.VideoView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

private lateinit var videoView: VideoView
class MainActivity : AppCompatActivity() {
    private var musica: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Reproducción de música
        musica = MediaPlayer.create(this, R.raw.hedwi)
        musica?.isLooping = true
        musica?.start()

        // Configuración del video
        videoView = findViewById(R.id.videoView)
        val videoPath = "android.resource://${packageName}/${R.raw.hogwarts}"
        val uri = Uri.parse(videoPath)

        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.setVolume(0f, 0f) // Silenciar el video si quieres solo música
            mediaPlayer.start()

            // Escalado
            videoView.post {
                val videoRatio = mediaPlayer.videoWidth.toFloat() / mediaPlayer.videoHeight
                val viewRatio = videoView.width.toFloat() / videoView.height
                val scale = if (videoRatio > viewRatio) {
                    videoView.height.toFloat() / mediaPlayer.videoHeight
                } else {
                    videoView.width.toFloat() / mediaPlayer.videoWidth
                }
                videoView.scaleX = scale
                videoView.scaleY = scale
            }
        }

        // Botón para comenzar
        val botonComenzar: Button = findViewById(R.id.botonComenzar)
        botonComenzar.setOnClickListener {
            musica?.stop()
            musica?.release()
            musica = null

            val intent = Intent(this, PreguntaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musica?.release()
        musica = null
    }
}
