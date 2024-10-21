package com.barco.pokedex.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.barco.pokedex.api.PokeService
import com.barco.pokedex.model.PokemonDetails
import com.barco.pokedex.model.PokemonOverview
import kotlinx.coroutines.flow.Flow

class PokeRepository(
    private val pokeService: PokeService,
) {
    fun getPokemonList(): Flow<PagingData<PokemonOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PokemonListDataSource.NETWORK_PAGE_SIZE,
            ),
            pagingSourceFactory = { PokemonListDataSource(pokeService) }
        ).flow
    }

    suspend fun getPokemon(id: String): Result<PokemonDetails> {
        val apiResult = Result.runCatching {
            pokeService.getDetails(id)
        }
        return apiResult
    }
}
