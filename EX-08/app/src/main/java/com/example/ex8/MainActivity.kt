package com.example.ex8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var alertMessageEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alertMessageEditText = findViewById(R.id.alertMessageEditText)
        val alertButton = findViewById<Button>(R.id.alertButton)
        alertButton.setOnClickListener {
            displayAlertDialog()
        }
    }

    private fun displayAlertDialog() {
        val message = alertMessageEditText.text.toString()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Priya wants to say:")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            // Cancel button clicked, handle the action if needed
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}