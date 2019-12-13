package com.example.github.githubsearcher.adapter

import android.content.Context
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.example.github.githubsearcher.R
import com.example.github.githubsearcher.bean.RepoBean
import com.example.github.githubsearcher.bean.UserBean
import com.example.github.githubsearcher.modle.UserItem


class UsersRecyclerViewAdapte(
    context: Context,
    items: MutableList<UserItem>
) : TrackableRecyclerViewAdapter<Int, UserItem, UserItem.ViewHolder>(context, items) {



    var mOnItemClickListener: OnItemClickListener<UserItem>? = null


    override fun assignListeners(holder: UserItem.ViewHolder, position: Int, item: UserItem) {
        super.assignListeners(holder, position, item)

        with(item) {
            setOnItemClickListener(holder, mOnItemClickListener)
        }
    }
}