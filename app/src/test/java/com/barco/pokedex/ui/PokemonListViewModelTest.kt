package com.barco.pokedex.ui

import com.nhaarman.mockitokotlin2.mock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.barco.pokedex.data.PokeRepository
import com.barco.pokedex.ui.pokemonlist.PokemonListViewModel
import io.mockk.coEvery
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PokemonListViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repository = mock<PokeRepository>()

    private val viewModel get() = PokemonListViewModel(repository)

    @Test
    fun fetchesAndParsesPokemonListSuccess() {
        coEvery { repository.getPokemonList() } returns Result.success(emptyList())
        runTest {
            viewModel.state.test {
                Assert.assertEquals(emptyList<String>(), awaitItem().pokemons)
            }
        }
    }
}
