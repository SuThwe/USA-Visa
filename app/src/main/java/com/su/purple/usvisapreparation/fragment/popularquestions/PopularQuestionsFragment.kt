package com.su.purple.usvisapreparation.fragment.popularquestions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.su.purple.usvisapreparation.R
import com.su.purple.usvisapreparation.adapter.PopularQuestionsAdapter
import com.su.purple.usvisapreparation.listener.OnItemClick
import com.su.purple.usvisapreparation.network.NetworkManager
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.app_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_popular_questions.*

class PopularQuestionsFragment : Fragment(R.layout.fragment_popular_questions) {

    private lateinit var viewModel: PopularQuestionsViewModel
    private var lastScrolledPosition = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(requireActivity())

        setUpToolbar()
        setUpViewModel()
        observeLiveData(view)

        activity?.let {
            if (NetworkManager().isNetworkAvailable(it)) {
                getPopularQuestions()
            }
            else {
                hideLoading()
                no_internet_layout.visibility = View.VISIBLE
            }
        }
    }

    private fun getPopularQuestions() {
        viewModel.getFireStoreData()
    }

    private fun observeLiveData(view: View) {
        viewModel.questionsList.observe(viewLifecycleOwner) {
            hideLoading()
            it?.let { list ->
                if (list.isNotEmpty()) {
                    val adapter = PopularQuestionsAdapter(list, object : OnItemClick {
                        override fun onClick(id: Int) {
                            val question = list.firstOrNull { x -> x.id == id }
                            question?.let { item ->
                                val action = PopularQuestionsFragmentDirections
                                    .actionPopularQuestionsFragmentToExplanationPopularQuestionsFragment(
                                        item
                                    )
                                view.findNavController().navigate(action)
                            }
                        }
                    })
                    recycler_view.adapter = adapter
                    (recycler_view.layoutManager as LinearLayoutManager).scrollToPosition(lastScrolledPosition)
                }
                else {
                    Toast.makeText(context, getString(R.string.something_went_wrong_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[PopularQuestionsViewModel::class.java]
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun setUpToolbar() {
        app_toolbar.setNavigationIcon(R.drawable.ic_back)
        app_toolbar.toolbar_title.text = resources.getString(R.string.popular_q_and_a)
        app_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        lastScrolledPosition = (recycler_view.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }
}