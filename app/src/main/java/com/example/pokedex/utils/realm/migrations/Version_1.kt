package com.example.pokedex.utils.realm.migrations

import com.example.pokedex.utils.realm.MigrationExecutable
import io.realm.kotlin.dynamic.DynamicRealm

class Version_1 : MigrationExecutable {

    override fun run(realm: DynamicRealm) {}
    override fun drop(realm: DynamicRealm) {}
}