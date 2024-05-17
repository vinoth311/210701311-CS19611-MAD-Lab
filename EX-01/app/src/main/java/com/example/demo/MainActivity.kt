package com.example.demo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.demo.R.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        val rootView= findViewById<androidx.constraintlayout.widget.ConstraintLayout>(id.rootView)
        val textView: TextView = findViewById(id.text)
        val increaseButton: Button = findViewById(id.fontsize)
        val changetextcolor:Button=findViewById(id.fontcolor)
        val changebgcolor:Button=findViewById(id.bgcolor)

        increaseButton.setOnClickListener {
            // Increase font size by a certain factor
            val currentSize = textView.textSize
            val newSize = currentSize + 5 // You can adjust this value
            textView.textSize = newSize
        }
        changetextcolor.setOnClickListener{
            textView.setTextColor(Color.GREEN)
        }
        changebgcolor.setOnClickListener {
            rootView.setBackgroundColor(Color.BLUE)
        }
    }
}