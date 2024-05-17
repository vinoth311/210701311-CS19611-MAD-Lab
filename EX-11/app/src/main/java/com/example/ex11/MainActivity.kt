package com.example.ex11

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var phoneNumberEditText: EditText
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneNumberEditText = findViewById(R.id.editTextPhoneNumber)
        messageEditText = findViewById(R.id.editTextMessage)
        sendButton = findViewById(R.id.buttonSend)

        sendButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val message = messageEditText.text.toString()

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                sendMessage(phoneNumber, message)
            }
        }
    }

    private fun sendMessage(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val phoneNumber = phoneNumberEditText.text.toString()
                val message = messageEditText.text.toString()
                sendMessage(phoneNumber, message)
            } else {
                Toast.makeText(
                    this,
                    "Permission denied, message cannot be sent",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }
}
