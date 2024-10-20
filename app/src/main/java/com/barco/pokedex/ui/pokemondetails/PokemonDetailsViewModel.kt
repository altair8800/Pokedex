package com.barco.pokedex.ui.pokemondetails

import androidx.lifecycle.viewModelScope
import com.barco.pokedex.data.PokeRepository
import com.barco.pokedex.model.PokemonDetails
import com.barco.pokedex.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokeRepository
) : BaseViewModel<PokemonDetailsState>(PokemonDetailsState()) {

    fun getPokemon(id: String) = viewModelScope.launch {
        setState { copy(refreshing = true) }
        val result = repository.getPokemon(id)
        result.onSuccess {
            setState { copy(details = it, refreshing = false) }
        }.onFailure {
            setState { copy(refreshing = false) }
        }
    }
}

data class PokemonDetailsState(
    val refreshing: Boolean = true,
    val details: PokemonDetails? = null
)
