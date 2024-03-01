package com.example.pokedex.repositories

import android.util.Log
import com.example.pokedex.models.PokemonModel
import com.example.pokedex.utils.Constants.Companion.RETRY
import com.example.pokedex.utils.Constants.Companion.TIME_OUT
import com.example.pokedex.utils.retrofit.RetrofitClient.api
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class PokemonListRepository {

    fun getPokemonList() = api.getDataPokemons()
        .timeout(TIME_OUT, TimeUnit.SECONDS)
        .retry(RETRY)
        .map { response ->
            response.results
        }
        .doOnError {
            Log.e("pokemon-fail", it.toString())
        }

    fun getPokemonDetaill(name: String) = api.getDataPokemonDetaill(name)
        .timeout(TIME_OUT, TimeUnit.SECONDS)
        .retry(RETRY)
        .map { response ->
            response
        }
        .doOnError {
            Log.e("pokemon-fail", it.toString())
        }

}