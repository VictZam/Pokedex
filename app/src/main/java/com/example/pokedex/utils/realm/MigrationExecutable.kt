package com.example.pokedex.utils.realm

import com.example.pokedex.utils.realm.migrations.Version_1
import io.realm.kotlin.dynamic.DynamicRealm

interface MigrationExecutable {

    companion object {

        private val EXECS = listOf(
            Version_1()
        )
        val CURRENT_VERSION = EXECS.size.toLong()

        fun getExecutables(oldVersion: Long, newVersion: Long): List<MigrationExecutable> {
            val firstIndex = oldVersion.toInt()
            val lastIndex = newVersion.toInt()

            return if (firstIndex < lastIndex)
                EXECS.subList(firstIndex, lastIndex)
            else
                EXECS.subList(lastIndex, firstIndex).reversed()
        }
    }

    fun run(realm: DynamicRealm)
    fun drop(realm: DynamicRealm)

}