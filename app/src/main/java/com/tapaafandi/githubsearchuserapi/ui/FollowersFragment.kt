package com.tapaafandi.githubsearchuserapi.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapaafandi.githubsearchuserapi.R
import com.tapaafandi.githubsearchuserapi.adapters.FollowersAdapter
import com.tapaafandi.githubsearchuserapi.databinding.FragmentFollowersBinding
import com.tapaafandi.githubsearchuserapi.utils.Constants.Companion.ARG_USERNAME
import com.tapaafandi.githubsearchuserapi.viewmodels.FollowersViewModel


class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var followersAdapter: FollowersAdapter

    companion object {
        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowersBinding.bind(view)
        showLoading(true)

        followersAdapter = FollowersAdapter()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)

        requestFollowersData()

        viewModel.getFollowersList().observe(requireActivity(), { listOfFollowers ->
            if (listOfFollowers != null) {
                followersAdapter.setData(listOfFollowers)
                showLoading(false)
            }
        })

        binding.apply {
            rvFollowersUser.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = followersAdapter
            }
        }
    }

    private fun requestFollowersData() {
        val username = arguments?.getString(ARG_USERNAME)
        if (username != null) {
            viewModel.getFollowersUser(username)
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