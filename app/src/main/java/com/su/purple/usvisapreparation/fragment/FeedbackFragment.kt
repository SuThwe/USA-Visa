package com.su.purple.usvisapreparation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.su.purple.usvisapreparation.MainActivity
import com.su.purple.usvisapreparation.R
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.app_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_feedback.*

class FeedbackFragment : Fragment(R.layout.fragment_feedback) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()

        btn_send_feedback.setOnClickListener {
            (activity as MainActivity)?.emailIntent(
                edit_name.text.toString(),
                edit_feedback.text.toString()
            )
        }

        edit_feedback.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }
    }

    private fun setUpToolbar() {
        app_toolbar.setNavigationIcon(R.drawable.ic_back)
        app_toolbar.toolbar_title.text = resources.getString(R.string.feedback)
        app_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}