package com.example.pokedex.utils.realm

import io.realm.kotlin.dynamic.DynamicRealm
import io.realm.kotlin.migration.RealmMigration


/*class DBMigration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val execs = MigrationExecutable.Companion.getExecutables(
            oldVersion,
            newVersion
        )

        execs.forEach {
            if (oldVersion < newVersion)
                it.run(realm)
            else
                it.drop(realm)
        }
    }

}*/