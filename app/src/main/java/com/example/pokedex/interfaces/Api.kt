package com.example.pokedex.interfaces

import com.example.pokedex.models.PokemonDetaillModel
import com.example.pokedex.models.PokemonListModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("pokemon")
    fun getDataPokemons(
    ): Single<PokemonListModel>


    @GET("pokemon/{name}")
    fun getDataPokemonDetaill(
        @Path("name") name: String
    ): Single<PokemonDetaillModel>

}