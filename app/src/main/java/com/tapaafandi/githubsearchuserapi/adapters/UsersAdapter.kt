package com.tapaafandi.githubsearchuserapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tapaafandi.githubsearchuserapi.R
import com.tapaafandi.githubsearchuserapi.data.network.models.Users
import com.tapaafandi.githubsearchuserapi.databinding.UserItemBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private val mData = ArrayList<Users>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<Users>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemBinding.bind(itemView)
        fun bind(userItems: Users) {
            binding.tvUsername.text = userItems.username

            Glide.with(itemView.context)
                .load(userItems.avatarUrl)
                .into(binding.ivUserImage)


            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(userItems) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UsersViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}

