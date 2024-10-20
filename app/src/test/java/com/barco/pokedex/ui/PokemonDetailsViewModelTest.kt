package com.barco.pokedex.ui

import app.cash.turbine.test
import com.barco.pokedex.data.PokeRepository
import com.barco.pokedex.model.PokemonDetails
import com.barco.pokedex.ui.pokemondetails.PokemonDetailsState
import com.barco.pokedex.ui.pokemondetails.PokemonDetailsViewModel
import com.barco.pokedex.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PokemonDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mockk<PokeRepository>()
    private val viewModel = PokemonDetailsViewModel(repository)
    private val pokemonDetails = mockk<PokemonDetails>()

    @Test
    fun fetchesAndParsesPokemonError() {
        val apiError = Error("Error")
        coEvery {
            repository.getPokemon("1")
        } returns Result.failure(apiError)

        runTest {
            viewModel.getPokemon("1")
            viewModel.state.test {
                val state = awaitItem()
                Assert.assertEquals(
                    state, PokemonDetailsState(refreshing = false, details = null)
                )
            }
        }
    }

    @Test
    fun fetchesAndParsesPokemonSuccess() {
        coEvery {
            repository.getPokemon("1")
        } returns (Result.success(pokemonDetails))

        runTest {
            viewModel.state.test {
                val initialState = awaitItem()
                Assert.assertEquals(
                    initialState,
                    PokemonDetailsState(refreshing = true, details = null)
                )
                viewModel.getPokemon("1")
                val state = awaitItem()
                Assert.assertEquals(
                    state,
                    PokemonDetailsState(refreshing = false, details = pokemonDetails)
                )
            }
        }
    }
}