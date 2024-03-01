package com.example.pokedex

import android.content.Context

class Preferences(context: Context) {

    private var fileName = context.getString(R.string.app_name_preference)
    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    var userName: String?
        get() = prefs.getString("userName", null)
        set(value) = prefs.edit().putString("userName", value).apply()

    var userPass: String?
        get() = prefs.getString("userPass", null)
        set(value) = prefs.edit().putString("userPass", value).apply()

}