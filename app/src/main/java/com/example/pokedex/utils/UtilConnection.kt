package com.example.pokedex.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


fun Context.hasInternet(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork != null
}


