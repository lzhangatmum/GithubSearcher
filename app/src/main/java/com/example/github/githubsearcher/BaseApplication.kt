package com.example.github.githubsearcher

import android.app.Application
import android.content.Intent
import com.lzy.okgo.OkGo
import okhttp3.OkHttpClient
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class BaseApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        OkGo.getInstance().init(this);
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(loggingInterceptor)

    }

}