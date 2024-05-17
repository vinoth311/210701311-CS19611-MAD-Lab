package com.example.ex14

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var micButton: ImageButton
    private lateinit var textView: TextView
    private lateinit var speechRecognizer: SpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        micButton = findViewById(R.id.micButton)
        textView = findViewById(R.id.textView)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
        }

        micButton.setOnClickListener {
            startListening()
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                textView.text = "Error: $error"
            }
            override fun onResults(results: Bundle?) {
                val result = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                textView.text = result?.get(0) ?: ""
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechRecognizer.startListening(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }
}
