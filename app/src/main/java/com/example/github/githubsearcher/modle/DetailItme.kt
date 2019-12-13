package com.example.github.githubsearcher.modle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.listeners.ItemClickListener
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.bumptech.glide.Glide
import com.example.github.githubsearcher.R
import com.example.github.githubsearcher.bean.RepoBean


class DetailItme (itemModel: RepoBean):  BaseItem<RepoBean, DetailItme.ViewHolder, UserResources>(itemModel) {


    companion object {

        const val MAIN_LAYOUT = R.layout.itme_repo_list

    }

    override fun init(
        adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
        parent: ViewGroup, inflater: LayoutInflater, resources: UserResources?
    ): ViewHolder {
        return ViewHolder(
            inflater.inflate(
                MAIN_LAYOUT,
                parent,
                false
            )
        )
    }

    override fun bind(
        adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
        viewHolder: ViewHolder, resources: UserResources?
    ) {
        super.bind(adapter, viewHolder, resources)

        val user = itemModel
        val context = viewHolder.itemView.context

        viewHolder.text_following.text = user.forks_count.toString()
        viewHolder.text_star.text = user.stargazers_count.toString()
        viewHolder.text_name.text = user.full_name


    }


    override fun getLayout(): Int = MAIN_LAYOUT


    class ViewHolder(itemView: View) : BaseItem.ViewHolder<RepoBean>(itemView) {

        val text_following = itemView.findViewById<TextView>(R.id.text_following)
        val text_star = itemView.findViewById<TextView>(R.id.text_star)
        val text_name = itemView.findViewById<TextView>(R.id.text_name)

    }
}