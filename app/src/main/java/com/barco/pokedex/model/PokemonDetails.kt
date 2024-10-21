package com.barco.pokedex.model

import com.squareup.moshi.Json

data class PokemonDetails(
    val name: String,
    val height: Int,
    val sprites: Sprites
)

data class Sprites( //TODO: Use Kotlin Serialization
    @Json(name = "front_default") val frontDefault: String
)
