package com.example.pokedex.repositories

import io.reactivex.Single

class LoginRepository {

    fun doLogin(user: String, password: String): Single<String> =
        Single.create<String> { emitter ->
            when {
                user.isEmpty() ->  emitter.onError(RuntimeException())
                password.isEmpty() ->  emitter.onError(RuntimeException())
                else ->  emitter.onSuccess(user)
            }
        }

}