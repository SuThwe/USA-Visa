package com.su.purple.usvisapreparation.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.su.purple.usvisapreparation.R
import com.su.purple.usvisapreparation.helper.AppConfig
import com.su.purple.usvisapreparation.helper.PreferenceHelper.appointmentCity
import com.su.purple.usvisapreparation.helper.PreferenceHelper.appointmentDate
import com.su.purple.usvisapreparation.helper.PreferenceHelper.appointmentTime
import kotlinx.android.synthetic.main.fragment_appointment.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var pref: SharedPreferences? = AppConfig.mPrefs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpData()
        onBackPressedCallBack()

        ic_email.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFeedbackFragment()
            view.findNavController().navigate(action)
        }

        btn_appointment.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAppointmentFragment()
            view.findNavController().navigate(action)
        }

        cardview_things_to_know.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToThingsToKnowFragment()
            view.findNavController().navigate(action)
        }

        cardview_q_and_a.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPopularQuestionsFragment()
            view.findNavController().navigate(action)
        }

        cardview_practice.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPracticeFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun setUpData() {
        val date = pref?.appointmentDate
        val time = pref?.appointmentTime
        val city = pref?.appointmentCity

        if(!date.equals("") || !time.equals("") || !city.equals("")) {
            layout_appointment.visibility = View.VISIBLE
            tv_date.text = date
            tv_time.text = time
            tv_city.text = city
        }
        else {
            tv_no_appointment.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        setUpData()
    }

    private fun onBackPressedCallBack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().moveTaskToBack(true)
            }
        })
    }

}