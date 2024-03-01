package com.example.pokedex.utils.retrofit

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHolder {

    var okHttpClient = OkHttpClient()

    val retrofit: Retrofit
        get() = mOldRetrofit

    var isHostChanged: Boolean = false
        private set

    private var mHost: String = "https://pokeapi.co/api/v2/"
    private var mOldRetrofit = buildRetrofit()

    private fun buildRetrofit() =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mHost)
            .client(okHttpClient)
            .build()


    fun setHost(url: String): Boolean {
        val fixedUrl = fixUrl(url)
        isHostChanged = fixedUrl != mHost
        if (isHostChanged) {
            Log.e("RETROFIT HOLDER", "SETTING URL: $url")
            mHost = fixedUrl
            mOldRetrofit = buildRetrofit()
        }
        return isHostChanged
    }

    fun <T> create(clazz: Class<T>): T {
        isHostChanged = false
        return retrofit.create(clazz)
    }

    private fun fixUrl(url: String): String {
        var newUrl: String = url
        if (!url.startsWith("http://", true) && !url.startsWith("https://") ) {
            newUrl = "http://$newUrl"
        }
        if (!url.endsWith("/")) {
            newUrl = "$newUrl/"
        }
        return newUrl
    }
}