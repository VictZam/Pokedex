package com.example.pokedex.utils.retrofit

import android.util.Log
import com.example.pokedex.interfaces.Api
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object RetrofitClient {

    var BASE_URL = "https://pokeapi.co/api/v2/"
    private val gson = Gson()

    private val okHttpClient = OkHttpClient.Builder().addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder().method(original.method(), original.body())
        val request = requestBuilder.build()
        Log.i("URL", request.url().toString())
        Log.i("BODY", gson.toJson(request.body()))
        it.proceed(request)
    }.build()

    val api: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }

}