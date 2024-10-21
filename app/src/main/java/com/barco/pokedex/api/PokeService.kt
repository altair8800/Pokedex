package com.barco.pokedex.api

import com.barco.pokedex.model.PokemonDetails
import com.barco.pokedex.model.PokemonOverview
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {
    @GET("pokemon")
    suspend fun getList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonList

    @GET("pokemon/{pokemonId}")
    suspend fun getDetails(
        @Path("pokemonId") pokemonId: String,
    ): PokemonDetails
}

data class PokemonList( //TODO: consume next,prev URLs
    val results: List<PokemonOverview>
)
