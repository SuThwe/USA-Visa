package com.su.purple.usvisapreparation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.su.purple.usvisapreparation.helper.AppConfig
import com.su.purple.usvisapreparation.util.Constants

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null) {
            supportActionBar?.hide()
            setUpAppConfig()
        }
    }

    private fun setUpAppConfig() {
        AppConfig.setPreferences(this)
    }

    @SuppressLint("IntentReset")
    fun sendEmail(name: String, feedback: String) {
        val sendTo = Constants.FEEDBACK_EMAIL
        val subject = Constants.FEEDBACK_EMAIL_SUBJECT
        val body = "$feedback\n\nBest regards,\n$name"

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.setData(Uri.parse("mailto:"))
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(sendTo));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        emailIntent.setType("text/plain")

        startActivity(emailIntent)
    }
}