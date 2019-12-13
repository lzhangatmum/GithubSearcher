package com.example.github.githubsearcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.example.github.githubsearcher.adapter.UsersRecyclerViewAdapte
import com.example.github.githubsearcher.bean.UserListBean
import com.example.github.githubsearcher.modle.DemoModes
import com.example.github.githubsearcher.modle.UserItem
import com.example.github.githubsearcher.utils.*
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.paulrybitskyi.persistentsearchview.adapters.model.SuggestionItem
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchConfirmedListener
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchQueryChangeListener
import com.paulrybitskyi.persistentsearchview.listeners.OnSuggestionChangeListener
import com.paulrybitskyi.persistentsearchview.utils.SuggestionCreationUtil
import com.paulrybitskyi.persistentsearchview.utils.VoiceRecognitionDelegate
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private var mDataProvider: DataProvider = DataProvider()


    private var mItems: MutableList<UserItem> = mutableListOf()


    private lateinit var mAdapter: UsersRecyclerViewAdapte


    private var mMode: DemoModes = DemoModes.WITHOUT_SUGGESTIONS



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(persistentSearchView) {
            setVoiceRecognitionDelegate(VoiceRecognitionDelegate(this@MainActivity))
            setOnSearchConfirmedListener(mOnSearchConfirmedListener)
            setOnSearchQueryChangeListener(mOnSearchQueryChangeListener)
            setOnSuggestionChangeListener(mOnSuggestionChangeListener)
            initRecyclerView()
            initProgressBar()
        }
    }


    private fun initProgressBar() {
        progressBar.makeGone()
    }


    override fun onResume() {
        super.onResume()

        // Fetching the search queries from the data provider
        val searchQueries = if (persistentSearchView.isInputQueryEmpty) {
            mDataProvider.getInitialSearchQueries()
        } else {
            mDataProvider.getSuggestionsForQuery(persistentSearchView.inputQuery)
        }

        // Converting them to recent suggestions and setting them to the widget
        persistentSearchView.setSuggestions(
            SuggestionCreationUtil.asRecentSearchSuggestions(
                searchQueries
            ), false
        )
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(VerticalSpacingItemDecorator(dpToPx(2), dpToPx(2)))

        mAdapter = UsersRecyclerViewAdapte(this, mItems)
        mAdapter.mOnItemClickListener = OnItemClickListener { _, item, index ->
            var intent = Intent(this@MainActivity,DetailActivity::class.java)
            intent.putExtra("login",item.itemModel.login);
            startActivity(intent)
        }
        recyclerView.adapter = mAdapter
//        recyclerView.addOnScrollListener(object : HeaderedRecyclerViewListener(this@MainActivity) {
//
//            override fun showHeader() {
//                AnimationUtils.showHeader(persistentSearchView)
//            }
//
//            override fun hideHeader() {
//                AnimationUtils.hideHeader(persistentSearchView)
//            }
//
//        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        VoiceRecognitionDelegate.handleResult(persistentSearchView, requestCode, resultCode, data)
    }


    private val mOnSearchConfirmedListener = OnSearchConfirmedListener { searchView, query ->
        saveSearchQueryIfNecessary(query)
        searchView.collapse()
        performSearch(query)
    }

    private fun saveSearchQueryIfNecessary(query: String?) {
        if (query != null) {
            mDataProvider.saveSearchQuery(query)
        }
    }

    private val mOnSearchQueryChangeListener = OnSearchQueryChangeListener { searchView, oldQuery, newQuery ->
        setSuggestions(if(newQuery.isBlank()) {
            mDataProvider.getInitialSearchQueries()
        } else {
            mDataProvider.getSuggestionsForQuery(newQuery)
        }, true)
    }





    private val mOnSuggestionChangeListener = object : OnSuggestionChangeListener {

        override fun onSuggestionPicked(suggestion: SuggestionItem) {
            val query = suggestion.itemModel.text

            saveSearchQueryIfNecessary(query)
            setSuggestions(mDataProvider.getSuggestionsForQuery(query), false)
            performSearch(query)
        }

        override fun onSuggestionRemoved(suggestion: SuggestionItem) {
            mDataProvider.removeSearchQuery(suggestion.itemModel.text)
        }


    }


    private fun performSearch(query: String) {
        emptyViewLl.makeGone()
        recyclerView.alpha = 0f
        progressBar.makeVisible()
        mAdapter.clear()

        OkGo.get<String>(URL.baseUrl+"search/users")
            .tag(this)
            .params("q",query)
            .headers("Accept","application/vnd.github.mercy-preview+json")
            .execute(object : StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                    var userListBean =  Gson().fromJson<UserListBean>(response!!.body(),
                        UserListBean::class.java)
                     mItems = userListBean.items.map { UserItem(it) }.toMutableList()
                }
            })
//        mItems = mDataProvider.generateUsers(query).map { UserItem(it) }.toMutableList()

        val runnable = Runnable {
            persistentSearchView.hideProgressBar(false)
            persistentSearchView.showLeftButton()

            mAdapter.items = mItems
            progressBar.makeGone()
            recyclerView.animate()
                .alpha(1f)
                .setInterpolator(LinearInterpolator())
                .setDuration(300L)
                .start()
        }

        Handler().postDelayed(runnable, 1000L)

        persistentSearchView.hideLeftButton(false)
        persistentSearchView.showProgressBar()
    }

    private fun initEmptyView() {
        if(mItems.isNotEmpty()) {
            emptyViewLl.visibility = if(mItems.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setSuggestions(queries: List<String>, expandIfNecessary: Boolean) {
        if(mMode == DemoModes.WITHOUT_SUGGESTIONS) {
            return
        }

        val suggestions: List<SuggestionItem> = when(mMode) {
            DemoModes.RECENT_SUGGESTIONS -> SuggestionCreationUtil.asRecentSearchSuggestions(queries)
            DemoModes.REGULAR_SUGGESTIONS -> SuggestionCreationUtil.asRegularSearchSuggestions(queries)

            else -> throw IllegalStateException()
        }

        persistentSearchView.setSuggestions(suggestions, expandIfNecessary)
    }

}


