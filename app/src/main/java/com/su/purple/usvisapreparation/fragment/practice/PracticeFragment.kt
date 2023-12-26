package com.su.purple.usvisapreparation.fragment.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import com.su.purple.usvisapreparation.R
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.app_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_practice.*

class PracticeFragment : Fragment(R.layout.fragment_practice) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()

        btn_start.setOnClickListener {
            val action = PracticeFragmentDirections.actionPracticeFragmentToPracticeQuestionFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun setUpToolbar() {
        app_toolbar.setNavigationIcon(R.drawable.ic_back)
        app_toolbar.toolbar_title.text = resources.getString(R.string.practice)
        app_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}