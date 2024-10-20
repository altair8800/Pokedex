package com.barco.pokedex.model

data class PokemonDetails(
    val name: String,
    val height: Int,
    val sprites: Sprites
)

data class Sprites(
    val front_default: String
)
