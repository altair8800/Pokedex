package com.barco.pokedex.data

import com.barco.pokedex.api.PokeService
import com.barco.pokedex.model.PokemonDetails
import com.barco.pokedex.model.PokemonOverview

class PokeRepository(
    private val pokeService: PokeService,
) {
    suspend fun getPokemonList(): Result<List<PokemonOverview>> {
        return Result.runCatching { pokeService.getList().results }
    }

    suspend fun getPokemon(id: String): Result<PokemonDetails> {
        val apiResult = Result.runCatching {
            pokeService.getDetails(id)
        }
        return apiResult
    }
}
