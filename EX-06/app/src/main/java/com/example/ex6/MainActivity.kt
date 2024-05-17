package com.example.ex6
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val validateButton: Button = findViewById(R.id.validateButton)
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val idEditText: EditText = findViewById(R.id.idEditText)

        validateButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val id = idEditText.text.toString().trim()

            if (username.isEmpty() || id.isEmpty()) {
                // Check if any field is empty
                // Show error message as toast
                Toast.makeText(this, "Username and ID cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!username.matches(Regex("[a-zA-Z]+"))) {
                // Check if username contains only alphabets
                // Show error message as toast
                Toast.makeText(this, "Username should contain only alphabets", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!id.matches(Regex("\\d{4}"))) {
                // Check if ID contains exactly 4 digits
                // Show error message as toast
                Toast.makeText(this, "ID should contain exactly 4 digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // If all validations pass, start MainActivity2
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("username", username)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }
}
