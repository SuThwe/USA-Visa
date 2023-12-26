package com.su.purple.usvisapreparation.fragment.thingstoknow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.su.purple.usvisapreparation.R
import com.su.purple.usvisapreparation.adapter.ThingsToKnowAdapter
import com.su.purple.usvisapreparation.listener.OnItemClick
import com.su.purple.usvisapreparation.network.NetworkManager
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.app_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_popular_questions.*
import kotlinx.android.synthetic.main.fragment_things_to_know.*
import kotlinx.android.synthetic.main.fragment_things_to_know.loading
import kotlinx.android.synthetic.main.fragment_things_to_know.no_internet_layout
import kotlinx.android.synthetic.main.fragment_things_to_know.recycler_view

class ThingsToKnowFragment : Fragment(R.layout.fragment_things_to_know) {

    private lateinit var viewModel: ThingsToKnowViewModel
    private var lastScrolledPosition = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(requireActivity())

        setUpToolbar()
        setUpViewModel()
        observeLiveData(view)

        activity?.let {
            if (NetworkManager().isNetworkAvailable(it)) {
                getThingsToKnowList()
            }
            else {
                hideLoading()
                no_internet_layout.visibility = View.VISIBLE
            }
        }
    }

    private fun getThingsToKnowList() {
        viewModel.getFireStoreData()
    }

    private fun observeLiveData(view: View) {
        viewModel.thingsToKnowList.observe(viewLifecycleOwner) {
            hideLoading()
            it?.let { list ->
                if (list.isNotEmpty()) {
                    val adapter = ThingsToKnowAdapter(list, object : OnItemClick {
                        override fun onClick(id: Int) {
                            val thingsToKnow = list.firstOrNull { x -> x.id == id }
                            thingsToKnow?.let { item ->
                                val action = ThingsToKnowFragmentDirections.actionThingsToKnowFragmentToExplanationThingsToKnowFragment(
                                    item.title,
                                    item.explanation
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
        viewModel = ViewModelProvider(this)[ThingsToKnowViewModel::class.java]
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun setUpToolbar() {
        app_toolbar.setNavigationIcon(R.drawable.ic_back)
        app_toolbar.toolbar_title.text = resources.getString(R.string.things_to_know)
        app_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        lastScrolledPosition = (recycler_view.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }
}