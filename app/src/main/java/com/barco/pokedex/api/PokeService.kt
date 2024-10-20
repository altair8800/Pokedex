package com.barco.pokedex.api

import com.barco.pokedex.model.PokemonDetails
import com.barco.pokedex.model.PokemonOverview
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {
    @GET("pokemon")
    suspend fun getList(): PokemonList

    @GET("pokemon/{pokemonId}")
    suspend fun getDetails(
        @Path("pokemonId") pokemonId: String,
    ): PokemonDetails
}

data class PokemonList(
    val results: List<PokemonOverview>
)
