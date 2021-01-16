package com.tapaafandi.githubsearchuserapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapaafandi.githubsearchuserapi.R
import com.tapaafandi.githubsearchuserapi.adapters.FollowingAdapter
import com.tapaafandi.githubsearchuserapi.databinding.FragmentFollowingBinding
import com.tapaafandi.githubsearchuserapi.utils.Constants.Companion.ARG_USERNAME
import com.tapaafandi.githubsearchuserapi.viewmodels.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var followingAdapter: FollowingAdapter

    companion object {

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowingBinding.bind(view)
        showLoading(true)
        followingAdapter = FollowingAdapter()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

        requestFollowingData()

        viewModel.getFollowingList().observe(requireActivity(), { listOfFollowing ->
            if (listOfFollowing != null) {
                followingAdapter.setData(listOfFollowing)
                showLoading(false)
            }
        })

        binding.apply {
            rvFollowingUser.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = followingAdapter
            }
        }
    }

    private fun requestFollowingData() {
        val username = arguments?.getString(ARG_USERNAME)
        if (username != null) {
            viewModel.getFollowingUser(username)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbSearchUser.visibility = View.VISIBLE
            binding.tvSearching.visibility = View.VISIBLE
        } else {
            binding.pbSearchUser.visibility = View.INVISIBLE
            binding.tvSearching.visibility = View.INVISIBLE
        }
    }

}