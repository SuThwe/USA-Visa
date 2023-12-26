package com.su.purple.usvisapreparation.fragment.popularquestions.explanation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.su.purple.usvisapreparation.R
import com.su.purple.usvisapreparation.model.PopularQuestion
import com.su.purple.usvisapreparation.util.hideKeyboard
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.app_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_explanation_popular_questions.*
import kotlinx.android.synthetic.main.fragment_explanation_popular_questions.tv_explanation
import kotlinx.android.synthetic.main.fragment_explanation_popular_questions.tv_title

class ExplanationPopularQuestionsFragment : Fragment(R.layout.fragment_explanation_popular_questions) {
    private val args: ExplanationPopularQuestionsFragmentArgs by navArgs()

    private lateinit var viewModel: ExplanationQuestionViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popularQuestion = args.popularQuestion

        setUpToolbar()
        setUpViewModel()

        tv_title.text = popularQuestion.title
        tv_explanation.text = HtmlCompat.fromHtml(popularQuestion.explanation, 0)
        tv_example.text = HtmlCompat.fromHtml(popularQuestion.example, 0)

        val savedAnswer = viewModel.getSavedAnswer(popularQuestion.title)
        if(savedAnswer != "") {
            edit_answer.setText(savedAnswer)
        }

        btn_save.setOnClickListener {
            saveAnswer(popularQuestion)
            it.hideKeyboard()
        }

        edit_answer.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[ExplanationQuestionViewModel::class.java]

        viewModel.savedAnswer.observe(viewLifecycleOwner) {
            it?.let {
                dismissLoading()
                if (it) { savedAnswerSuccessfully() }
            }
        }
    }

    private fun saveAnswer(popularQuestion: PopularQuestion) {
        if (edit_answer.text.isEmpty()) {
            return
        }

        popularQuestion.saved_answer = edit_answer.text.toString()
        showLoading()
        viewModel.saveUserAnswer(popularQuestion)
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun dismissLoading() {
        loading.visibility = View.GONE
    }

    private fun savedAnswerSuccessfully() {
        Toast.makeText(requireActivity(), "Your answer is saved successfully", Toast.LENGTH_SHORT).show()
    }

    private fun setUpToolbar() {
        app_toolbar.setNavigationIcon(R.drawable.ic_back)
        app_toolbar.toolbar_title.text = resources.getString(R.string.popular_q_and_a)
        app_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}