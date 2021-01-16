package com.tapaafandi.githubsearchuserapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.tapaafandi.githubsearchuserapi.R
import com.tapaafandi.githubsearchuserapi.adapters.FollowersAndFollowingPagerAdapter
import com.tapaafandi.githubsearchuserapi.data.network.models.Users
import com.tapaafandi.githubsearchuserapi.databinding.ActivityDetailUserBinding
import com.tapaafandi.githubsearchuserapi.viewmodels.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var followersAndFollowingPagerAdapter: FollowersAndFollowingPagerAdapter
    private lateinit var viewPager: ViewPager

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubSearchUserApi)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPagerConfiguration()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        getUserDetail()

        viewModel.getDetailUser().observe(this, { userDetail ->

            with(binding) {
                tvName.text = userDetail.name
                tvUsername.text = userDetail.username
                tvBio.text = userDetail.bio
                tvCompany.text = userDetail.company
                tvLocation.text = userDetail.location
                tvRepository.text = userDetail.repository
                tvFollower.text = userDetail.followers
                tvFollowing.text = userDetail.following
            }

            Glide.with(this)
                .load(userDetail.avatarUrl)
                .into(binding.ivUserImage)

            supportActionBar?.title = userDetail.username
        })
    }

    private fun viewPagerConfiguration() {
        followersAndFollowingPagerAdapter = FollowersAndFollowingPagerAdapter(this, supportFragmentManager)
        viewPager = binding.vpFollowerAndFollowing
        viewPager.adapter = followersAndFollowingPagerAdapter
        val tabs: TabLayout = binding.tlFollowerAndFollowing
        tabs.setupWithViewPager(viewPager)
    }

    private fun getUserDetail() {
        val user = intent.getParcelableExtra<Users>(EXTRA_DETAIL) as Users
        val username = user.username
        viewModel.getUserDetailByUsername(username)

        if (username != null) {
            sendUsernameDataToFollowingFragment(username)
        }
    }

    private fun sendUsernameDataToFollowingFragment(username: String) {
        followersAndFollowingPagerAdapter.username = username
        viewPager.adapter = followersAndFollowingPagerAdapter
    }
}