package com.su.purple.usvisapreparation.fragment.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.su.purple.usvisapreparation.R
import com.su.purple.usvisapreparation.helper.AppConfig
import com.su.purple.usvisapreparation.model.PopularQuestion
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.app_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_practice_question.*
import kotlin.random.Random

class PracticeQuestionFragment : Fragment(R.layout.fragment_practice_question) {

    private lateinit var viewModel: PracticeQuestionViewModel
    private var questions: List<PopularQuestion> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setUpViewModel()
        observeLiveData(view)
        getQuestions()

        layout_hiding_answer.setOnClickListener {
            showAnswer()
        }

        tv_saved_answer.setOnClickListener {
            hideAnswer()
        }

        btn_done.setOnClickListener {
            val action = PracticeQuestionFragmentDirections.actionPracticeQuestionFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }

        btn_next.setOnClickListener {
            hideAnswer()
            getRandomQuestion()
        }
    }

    private fun getQuestions() {
        viewModel.getFireStoreData()
    }

    private fun observeLiveData(view: View) {
        viewModel.questionsList.observe(viewLifecycleOwner) {
            it?.let { list ->
                loading.visibility = View.GONE
                practice_layout.visibility = View.VISIBLE
                questions = list
                getRandomQuestion()
            }
        }
    }

    private fun getRandomQuestion() {
        if (questions.isNotEmpty()) {
            val randomNumber = Random.nextInt(0, questions.size-1)

            val question = questions[randomNumber].title
            tv_practice_question.text = question

            val answer = AppConfig.getSavedAnswer(question)
            tv_saved_answer.text = when (answer.isEmpty()) {
                true -> getString(R.string.you_havent_answered_for_this_question)
                false -> answer
            }
        }
    }

    private fun showAnswer() {
        layout_hiding_answer.visibility = View.GONE
        tv_saved_answer.visibility = View.VISIBLE
        tv_click_answer.text = getString(R.string.touch_to_hide_your_answer)
    }

    private fun hideAnswer() {
        tv_saved_answer.visibility = View.GONE
        layout_hiding_answer.visibility = View.VISIBLE
        tv_click_answer.text = getString(R.string.touch_to_see_your_answer)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[PracticeQuestionViewModel::class.java]
    }

    private fun setUpToolbar() {
        app_toolbar.setNavigationIcon(R.drawable.ic_back)
        app_toolbar.toolbar_title.text = resources.getString(R.string.practice)
        app_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}