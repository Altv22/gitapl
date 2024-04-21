package com.example.gitapl.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.gitapl.databinding.UserItemsBinding
import com.example.gitapl.mod.User


class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    private val list = ArrayList<User>()
    private var onItemClickCall: OnItemClickCall? = null

    fun setOnItemClickCall(onItemClickCall: OnItemClickCall) {
        this.onItemClickCall = onItemClickCall
    }

    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: UserItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
              onItemClickCall?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imageviewUser)
                textviewUsername.text = user.login

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCall{
        fun onItemClicked(data: User)
    }
}