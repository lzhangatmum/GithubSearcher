package com.example.github.githubsearcher.adapter

import android.content.Context
import android.widget.TextView
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.adapster.listeners.OnItemLongClickListener
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.github.githubsearcher.R
import com.example.github.githubsearcher.bean.RepoBean
import com.example.github.githubsearcher.modle.DetailItme
import com.example.github.githubsearcher.modle.UserItem

class DetailAdapter (dataSize: Int) : BaseQuickAdapter<RepoBean, BaseViewHolder>(R.layout.itme_repo_list, listOf<RepoBean>()) {
    override fun convert(helper: BaseViewHolder, item: RepoBean?) {
        (helper.getView<TextView>(R.id.text_following)).text = item!!.forks_count.toString()
        (helper.getView<TextView>(R.id.text_star)).text = item!!.stargazers_count.toString()
        (helper.getView<TextView>(R.id.text_name)).text = item!!.full_name.toString()
    }



}