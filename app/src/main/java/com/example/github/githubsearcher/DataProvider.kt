package com.example.github.githubsearcher

import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.orhanobut.logger.Logger
import java.io.Serializable
import java.util.*

class DataProvider : Serializable {
    private var mInitialSearchQueries: MutableList<String> = mutableListOf()


    fun saveSearchQuery(searchQuery: String) {
        with(mInitialSearchQueries) {
            remove(searchQuery)
            add(0, searchQuery)
        }
    }

    fun getInitialSearchQueries(): List<String> {
        return mInitialSearchQueries
    }

    fun getSuggestionsForQuery(query: String): List<String> {
        val pickedSuggestions: MutableList<String> = mutableListOf()

        if(query.isEmpty()) {
            pickedSuggestions.addAll(mInitialSearchQueries)
        } else {
            mInitialSearchQueries.forEach {
                if(it.toLowerCase().startsWith(query.toLowerCase())) {
                    pickedSuggestions.add(it)
                }
            }
        }

        return pickedSuggestions
    }


    fun removeSearchQuery(searchQuery: String) {
        mInitialSearchQueries.remove(searchQuery)
    }


}