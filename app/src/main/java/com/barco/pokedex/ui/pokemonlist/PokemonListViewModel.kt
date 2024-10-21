package com.barco.pokedex.ui.pokemonlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.barco.pokedex.data.PokeRepository
import com.barco.pokedex.model.PokemonOverview
import com.barco.pokedex.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokeRepository
) : BaseViewModel<PokemonListState>(PokemonListState()) {
    init {
        getPokemonList()
    }

    private fun getPokemonList() = viewModelScope.launch {
        setState { copy(refreshing = true) }
        val pokemons = repository.getPokemonList()
        setState { copy(refreshing = false, pokemons = pokemons.cachedIn(viewModelScope)) }
    }
}

data class PokemonListState(
    val refreshing: Boolean = true,
    val pokemons: Flow<PagingData<PokemonOverview>> = emptyFlow()
)
