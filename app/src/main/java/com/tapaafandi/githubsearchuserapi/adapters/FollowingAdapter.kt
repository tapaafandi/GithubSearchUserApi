package com.tapaafandi.githubsearchuserapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tapaafandi.githubsearchuserapi.R
import com.tapaafandi.githubsearchuserapi.data.network.models.Users
import com.tapaafandi.githubsearchuserapi.databinding.UserItemBinding

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val followingData = ArrayList<Users>()

    fun setData(items: ArrayList<Users>) {
        followingData.clear()
        followingData.addAll(items)
        notifyDataSetChanged()
    }

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(followingData[position])
    }

    override fun getItemCount() = followingData.size
}