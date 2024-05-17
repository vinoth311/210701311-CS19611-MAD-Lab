package com.example.ex12

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //declare views
    private lateinit var mRecipientEt: EditText
    private lateinit var mSubjectEt: EditText
    private lateinit var mMessageEt: EditText
    private lateinit var mSendEmailBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializing views with activity_main.xml
        mRecipientEt = findViewById(R.id.recipientEt)
        mSubjectEt = findViewById(R.id.subjectEt)
        mMessageEt = findViewById(R.id.messageEt)
        mSendEmailBtn = findViewById(R.id.sendEmailBtn)

        //button click to get input and call sendEmail method
        mSendEmailBtn.setOnClickListener {
            //get input from EditTexts and save in variables
            val recipient = mRecipientEt.text.toString().trim()
            val subject = mSubjectEt.text.toString().trim()
            val message = mMessageEt.text.toString().trim()

            //method call for email intent with these inputs as parameters
            sendEmail(recipient, subject, message)
        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        //call email share method
        val gmailPackage = "com.google.android.gm"
        // return true if gmail is installed
        val isGmailInstalled = isAppInstalled(gmailPackage)

        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val intent = Intent(Intent.ACTION_SEND)
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put subject of email
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put message of email in intent
        intent.putExtra(Intent.EXTRA_TEXT, message)
        if (isGmailInstalled) {
            intent.type = "text/html"
            intent.setPackage(gmailPackage)
            startActivity(intent)
        } else {
            // allow user to choose a different app to send email with
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "choose an email client"))
        }
    }

    private fun isAppInstalled(packageName: String): Boolean {
        return try {
            packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}