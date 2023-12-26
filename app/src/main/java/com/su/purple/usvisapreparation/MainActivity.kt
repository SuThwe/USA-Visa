package com.su.purple.usvisapreparation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.su.purple.usvisapreparation.fragment.SplashFragment
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

    fun emailIntent(name: String, feedback: String) {
        val sendTo = Constants.FEEDBACK_EMAIL
        val subject = Constants.FEEDBACK_EMAIL_SUBJECT
        val body = "$feedback\n\nBest regards,\n$name"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(sendTo));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)

        // set type of intent
        intent.type = "message/rfc822"

        // startActivity with intent with chooser as Email client using createChooser function
        startActivity(Intent.createChooser(intent, "Choose an Email client :"))
    }
}