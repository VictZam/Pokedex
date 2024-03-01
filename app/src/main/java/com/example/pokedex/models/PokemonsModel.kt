package com.example.pokedex.models

import com.google.gson.annotations.SerializedName

open class PokemonListModel(
    @SerializedName("count") var count: Int,
    @SerializedName("next") var next: String,
    @SerializedName("previous") var previous: String,
    @SerializedName("results") var results: ArrayList<PokemonModel>,
)
open class PokemonModel (
    @SerializedName("name") var name: String,
    @SerializedName("detaill") var detaill: PokemonDetaillModel,
)

open class PokemonDetaillModel (
    @SerializedName("order") var order: Int,
    @SerializedName("sprites") var sprites: PokemonSpriteModel,
    @SerializedName("base_experience") var baseExperience: Int,
    @SerializedName("moves") var moves: ArrayList<PokemonMovesModel>,
)

open class PokemonSpriteModel(
    @SerializedName("front_default") var frontDefault: String,
    @SerializedName("back_default") var backDefault: String,
)

open class PokemonMovesModel(
    @SerializedName("move") var move: PokemonMoveModel,
)
open class PokemonMoveModel(
    @SerializedName("name") var name: String,
)