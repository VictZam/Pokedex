package com.example.pokedex

import android.content.Context
import android.content.SharedPreferences
import com.example.pokedex.models.PokemonModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Preferences(context: Context) {

    private var fileName = context.getString(R.string.app_name_preference)
    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    var userName: String?
        get() = prefs.getString("userName", null)
        set(value) = prefs.edit().putString("userName", value).apply()

    var userPass: String?
        get() = prefs.getString("userPass", null)
        set(value) = prefs.edit().putString("userPass", value).apply()

    fun saveArrayList(list: ArrayList<PokemonModel>, key: String) {
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun deleteArrayList(key: String) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.remove(key)
    }

    fun getArrayList(key: String): ArrayList<PokemonModel> {
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<PokemonModel>>() {}.type
        return gson.fromJson(json, type)
    }

}