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
import com.example.github.githubsearcher.bean.UserBean

class UserItem(itemModel: UserBean):  BaseItem<UserBean, UserItem.ViewHolder, UserResources>(itemModel) {


    companion object {

        const val MAIN_LAYOUT = R.layout.itme_user_list

    }

    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, com.arthurivanets.adapster.markers.ItemResources>>?,
                      parent: ViewGroup, inflater: LayoutInflater, resources: UserResources?): ViewHolder {
        return ViewHolder(inflater.inflate(
            MAIN_LAYOUT,
            parent,
            false
        ))
    }

    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: UserResources?) {
        super.bind(adapter, viewHolder, resources)

        val user = itemModel
        val context = viewHolder.itemView.context

        Glide.with(context)
            .load(user.avatar_url)
            .into(viewHolder.profileImageIv)
//        viewHolder.profileImageIv.setImageDrawable(ContextCompat.getDrawable(context, user.avatar_url))

        viewHolder.usernameTv.text = user.login

    }


    fun setOnItemClickListener(viewHolder: ViewHolder, onItemClickListener: OnItemClickListener<UserItem>?) {
        viewHolder.contentContainerRl.setOnClickListener(ItemClickListener(this, 0, onItemClickListener))
    }


    override fun getLayout(): Int = MAIN_LAYOUT


    class ViewHolder(itemView: View) : BaseItem.ViewHolder<UserBean>(itemView) {

        val usernameTv = itemView.findViewById<TextView>(R.id.usernameTv)

        val profileImageIv = itemView.findViewById<ImageView>(R.id.profileImageIv)

        val contentContainerRl = itemView.findViewById<RelativeLayout>(R.id.contentContainerRl)


    }

}


