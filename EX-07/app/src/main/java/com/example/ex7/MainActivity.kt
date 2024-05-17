package com.example.ex7

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileWriter
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextRegNo = findViewById<EditText>(R.id.editTextRegNo)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextCGPA = findViewById<EditText>(R.id.editTextCGPA)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonLoad = findViewById<Button>(R.id.buttonLoad)

        buttonSave.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION)
            } else {
                saveDataToFile(editTextRegNo.text.toString(), editTextName.text.toString(), editTextCGPA.text.toString())
            }
        }

        buttonLoad.setOnClickListener {
            loadDataFromFile()
        }
    }

    private fun saveDataToFile(regNo: String, name: String, cgpa: String) {
        val directory = Environment.getExternalStorageDirectory()
        val file = File(directory, "student_data.txt")
        try {
            val fileWriter = FileWriter(file, true)
            fileWriter.append("$regNo, $name, $cgpa\n")
            fileWriter.flush()
            fileWriter.close()
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadDataFromFile() {
        val directory = Environment.getExternalStorageDirectory()
        val file = File(directory, "student_data.txt")
        if (file.exists()) {
            val stringBuilder = StringBuilder()
            file.forEachLine { line ->
                stringBuilder.append(line).append("\n")
            }
            Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
