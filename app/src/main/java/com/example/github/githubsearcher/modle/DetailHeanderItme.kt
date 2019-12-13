package com.example.github.githubsearcher.modle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Header
import com.bumptech.glide.Glide
import com.example.github.githubsearcher.R
import com.example.github.githubsearcher.bean.UserBean

class DetailHeanderItme (itemModel: UserBean) : BaseItem<UserBean, DetailHeanderItme.ViewHolder, ItemResources>(itemModel),
    Header<DetailHeanderItme.ViewHolder> {
    override fun init(
        adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
        parent: ViewGroup,
        inflater: LayoutInflater,
        resources: ItemResources?
    ): ViewHolder {
        return DetailHeanderItme.ViewHolder(
            inflater.inflate(
                UserItem.MAIN_LAYOUT,
                parent,
                false
            )
        )    }

    override fun setOnItemClickListener(
           viewHolder: ViewHolder,
           onItemClickListener: OnItemClickListener<Header<ViewHolder>>?
       ) {
           TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
       }




    companion object {

        const val MAIN_LAYOUT = R.layout.itme_user_list

    }
    override fun getLayout(): Int  = MAIN_LAYOUT
    override fun bind(
        adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
        viewHolder: ViewHolder,
        resources: ItemResources?
    ) {
        super.bind(adapter, viewHolder, resources)
        val user = itemModel
        val context = viewHolder.itemView.context

        Glide.with(context)
            .load(user.avatar_url)
            .into(viewHolder.image_avater)
        viewHolder.text_name.text = user.login
        viewHolder.text_email.text = user.login
        viewHolder.text_location.text = user.login
        viewHolder.text_join_date.text = user.login
        viewHolder.text_follows.text = user.login
        viewHolder.text_following.text = user.login
    }



       class ViewHolder(itemView: View) : BaseItem.ViewHolder<UserBean>(itemView) {

        val text_name = itemView.findViewById<TextView>(R.id.text_name)

        val image_avater = itemView.findViewById<ImageView>(R.id.image_avatar)

        val text_email = itemView.findViewById<TextView>(R.id.text_email)

        val text_location = itemView.findViewById<TextView>(R.id.text_location)

        val text_join_date = itemView.findViewById<TextView>(R.id.text_join_date)

        val text_follows = itemView.findViewById<TextView>(R.id.text_follows)

        val text_following = itemView.findViewById<TextView>(R.id.text_following)

        val text_detail = itemView.findViewById<TextView>(R.id.text_detail)

    }

}