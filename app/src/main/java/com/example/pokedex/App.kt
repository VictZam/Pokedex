package com.example.pokedex

import android.app.Application
import com.example.pokedex.utils.AndroidDisposable

val preferences: Preferences by lazy { App.preferences }
val subscriptions: AndroidDisposable by lazy { App.mSubscriptions }

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var preferences: Preferences
        lateinit var mSubscriptions: AndroidDisposable
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        preferences = Preferences(this)
        mSubscriptions = AndroidDisposable()

        /*Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(MigrationExecutable.CURRENT_VERSION)
            .migration(DBMigration())
            .allowWritesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(config)*/
    }

}