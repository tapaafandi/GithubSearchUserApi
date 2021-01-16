package com.tapaafandi.githubsearchuserapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tapaafandi.githubsearchuserapi.R
import com.tapaafandi.githubsearchuserapi.data.network.models.Users
import com.tapaafandi.githubsearchuserapi.databinding.UserItemBinding

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private val followersData = ArrayList<Users>()

    fun setData(items: ArrayList<Users>) {
        followersData.clear()
        followersData.addAll(items)
        notifyDataSetChanged()
    }

    inner class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemBinding.bind(itemView)
        fun bind(userItems: Users) {
            binding.apply {
                tvUsername.text = userItems.username
                Glide.with(itemView.context)
                    .load(userItems.avatarUrl)
                    .into(ivUserImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return FollowersViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(followersData[position])
    }

    override fun getItemCount() = followersData.size

}