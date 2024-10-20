package com.barco.pokedex.ui.pokemonlist

import androidx.lifecycle.viewModelScope
import com.barco.pokedex.data.PokeRepository
import com.barco.pokedex.model.PokemonOverview
import com.barco.pokedex.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
        repository.getPokemonList()
            .onSuccess {
                setState { copy(refreshing = false, pokemons = it) }
            }
            .onFailure {
                setState { copy(refreshing = false) }
            }
    }
}

data class PokemonListState(
    val refreshing: Boolean = true,
    val pokemons: List<PokemonOverview> = emptyList()
)
