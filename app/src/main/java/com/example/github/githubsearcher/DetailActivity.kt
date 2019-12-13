package com.example.github.githubsearcher

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.model.BaseItem
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.github.githubsearcher.adapter.DetailAdapter
import com.example.github.githubsearcher.adapter.UsersRecyclerViewAdapte
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.reflect.TypeToken
import com.example.github.githubsearcher.bean.RepoBean
import com.example.github.githubsearcher.bean.UserBean
import com.example.github.githubsearcher.modle.DemoModes


class DetailActivity: AppCompatActivity(){

    private lateinit var rv_repo: RecyclerView
    private lateinit var detailAdapte: DetailAdapter
    private lateinit var headerView :View
    private lateinit var userBean: UserBean
    private lateinit var  mcArray : Array<RepoBean>

    private var mMode: DemoModes = DemoModes.RECENT_SUGGESTIONS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        rv_repo = findViewById(R.id.rv_repo)
        rv_repo.layoutManager = LinearLayoutManager(this)

        initData(intent.getStringExtra("login"))



    }
    fun initData(login: String){
        OkGo.get<String>(URL.baseUrl+"users/"+login)
            .tag(this)
            .params("q",login)
            .headers("Accept","application/vnd.github.mercy-preview+json")
            .execute(object : StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                     userBean =  Gson().fromJson<UserBean>(response!!.body(),
                        UserBean::class.java)
                    //addheader here
                    OkGo.get<String>(userBean.repos_url)
                        .tag(this)
                        .params("q",login)
                        .headers("Accept","application/vnd.github.mercy-preview+json")
                        .execute(object : StringCallback(){
                            override fun onSuccess(response: Response<String>?) {
                                 mcArray = Gson().fromJson(response!!.body(), Array<RepoBean>::class.java)
                                detailAdapte = DetailAdapter(mcArray.size)
                                detailAdapte.openLoadAnimation()
                                detailAdapte.setNewData(mcArray.asList())
                                headerView = getHeaderView()
                                detailAdapte.addHeaderView(headerView)
                                rv_repo.adapter = detailAdapte
                            }
                        })

                }
            })
    }
    fun getHeaderView(): View{
        val view: View = layoutInflater.inflate(R.layout.item_handler, rv_repo.parent as ViewGroup, false)

            Glide.with(this@DetailActivity)
                .load(userBean.avatar_url)
                .into(view.findViewById(R.id.image_avatar)as ImageView)
            (view.findViewById(R.id.text_name) as TextView).text =userBean.name
            (view.findViewById(R.id.text_email) as TextView).text =userBean.email
            (view.findViewById(R.id.text_location) as TextView).text =userBean.location
            (view.findViewById(R.id.text_join_date) as TextView).text =userBean.created_at.toString()
            (view.findViewById(R.id.text_follows) as TextView).text =userBean.followers.toString()
            (view.findViewById(R.id.text_following) as TextView).text =userBean.following.toString()
            (view.findViewById(R.id.persistentSearchView) as SearchView).setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    detailAdapte.setNewData( mcArray.asList().filter { x-> x.full_name.contains(newText as CharSequence) })
                    return true
                }

            })

        return view
    }
//    private val mOnSearchQueryChangeListener = OnSearchQueryChangeListener { searchView, oldQuery, newQuery ->
//        detailAdapte.setNewData( mcArray.asList().filter { x-> x.full_name.contains(newQuery) })
//    }



}
