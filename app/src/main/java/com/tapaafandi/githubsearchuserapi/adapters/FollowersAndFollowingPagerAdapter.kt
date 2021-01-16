package com.tapaafandi.githubsearchuserapi.adapters

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tapaafandi.githubsearchuserapi.R
import com.tapaafandi.githubsearchuserapi.ui.FollowersFragment
import com.tapaafandi.githubsearchuserapi.ui.FollowingFragment

class FollowersAndFollowingPagerAdapter(
    private val mContext : Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username: String? = null

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.following, R.string.follower)

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = username?.let { FollowingFragment.newInstance(it) }
            1 -> fragment = username?.let { FollowersFragment.newInstance(it) }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(TAB_TITLES[position])
    }
}
