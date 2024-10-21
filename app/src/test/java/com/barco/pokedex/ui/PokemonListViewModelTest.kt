package com.barco.pokedex.ui

import app.cash.turbine.test
import com.barco.pokedex.data.PokeRepository
import com.barco.pokedex.ui.pokemonlist.PokemonListViewModel
import com.barco.pokedex.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PokemonListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mockk<PokeRepository>()

    private val viewModel get() = PokemonListViewModel(repository)

    @Test
    fun fetchesAndParsesPokemonListSuccess() {
        every { repository.getPokemonList() } returns emptyFlow()
        runTest {
            viewModel.state.test {
                Assert.assertEquals(false, awaitItem().refreshing)
            }
        }
    }
}
