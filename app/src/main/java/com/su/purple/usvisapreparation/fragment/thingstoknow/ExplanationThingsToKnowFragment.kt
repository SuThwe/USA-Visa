package com.su.purple.usvisapreparation.fragment.thingstoknow

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import com.su.purple.usvisapreparation.R
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.app_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_explanation_things_to_know.*

class ExplanationThingsToKnowFragment : Fragment(R.layout.fragment_explanation_things_to_know) {
    private val args: ExplanationThingsToKnowFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = args.title
        val explanation = args.explanation

        setUpToolbar()
        tv_title.text = title
        tv_explanation.text = HtmlCompat.fromHtml(explanation, 0)
    }

    private fun setUpToolbar() {
        app_toolbar.setNavigationIcon(R.drawable.ic_back)
        app_toolbar.toolbar_title.text = resources.getString(R.string.things_to_know)
        app_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}