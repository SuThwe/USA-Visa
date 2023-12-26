package com.su.purple.usvisapreparation.fragment

import android.content.SharedPreferences
import com.su.purple.usvisapreparation.helper.datetimepicker.DatePickerFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.su.purple.usvisapreparation.R
import com.su.purple.usvisapreparation.helper.AppConfig
import com.su.purple.usvisapreparation.helper.PreferenceHelper
import com.su.purple.usvisapreparation.helper.PreferenceHelper.appointmentCity
import com.su.purple.usvisapreparation.helper.PreferenceHelper.appointmentDate
import com.su.purple.usvisapreparation.helper.PreferenceHelper.appointmentTime
import com.su.purple.usvisapreparation.helper.datetimepicker.DateTimePickerListener
import com.su.purple.usvisapreparation.helper.datetimepicker.TimePickerFragment
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.app_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_appointment.*
import java.text.SimpleDateFormat
import java.util.*

class AppointmentFragment : Fragment(R.layout.fragment_appointment), DateTimePickerListener {

    private var cal: Calendar = Calendar.getInstance()
    private var pref: SharedPreferences? = AppConfig.mPrefs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
        
//        edit_date.setOnClickListener { showDatePickerDialog(it) }
        edit_date.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                showDatePickerDialog(view)
            }
        }

//        edit_time.setOnClickListener { showTimePickerDialog(it) }
        edit_time.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                showTimePickerDialog(view)
            }
        }
        
        btn_save.setOnClickListener { 
            saveAppointmentData()
        }
    }

    private fun setUp() {
        setUpToolbar()
        setUpData()
    }

    private fun setUpToolbar() {
        app_toolbar.setNavigationIcon(R.drawable.ic_back)
        app_toolbar.toolbar_title.text = resources.getString(R.string.appointment)
        app_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setUpData() {
        edit_date.setText(pref?.appointmentDate)
        edit_time.setText(pref?.appointmentTime)
        edit_city.setText(pref?.appointmentCity)
    }

    private fun saveAppointmentData() {
        pref?.appointmentDate = edit_date.text.toString()
        pref?.appointmentTime = edit_time.text.toString()
        pref?.appointmentCity = edit_city.text.toString()
        requireActivity().onBackPressed()
    }

    private fun showDatePickerDialog(v: View) {
        val datePickerFragment = DatePickerFragment(this)
        requireActivity().supportFragmentManager?.let { datePickerFragment.show(it, "datePicker") }
    }

    private fun showTimePickerDialog(view: View) {
        val timePickFragment = TimePickerFragment(this)
        requireActivity().supportFragmentManager?.let { timePickFragment.show(it, "timePicker") }
    }

    override fun getDate(year: Int, month: Int, day: Int) {
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)

        val dateFormat = "dd MMM yyyy"
        val sdf = SimpleDateFormat(dateFormat)
        edit_date.setText(sdf.format(cal.time))
    }

    override fun getTime(hourOfDay: Int, minute: Int) {
        cal.set(Calendar.HOUR, hourOfDay)
        cal.set(Calendar.MINUTE, minute)

        val timeFormat = "KK:mm aaa"
        val sdf = SimpleDateFormat(timeFormat)
        edit_time.setText(sdf.format(cal.time))
    }
}