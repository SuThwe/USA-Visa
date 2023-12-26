package com.su.purple.usvisapreparation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.su.purple.usvisapreparation.MainActivity
import com.su.purple.usvisapreparation.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLetsGo = view.findViewById<AppCompatButton>(R.id.btn_lets_go)
        btnLetsGo?.setOnClickListener {
            if(view.findNavController().currentDestination?.id == R.id.splashFragment) {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                view.findNavController().navigate(action)
            }
        }
    }
}