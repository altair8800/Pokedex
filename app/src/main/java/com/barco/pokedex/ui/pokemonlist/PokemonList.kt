package com.barco.pokedex.ui.pokemonlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import okhttp3.HttpUrl.Companion.toHttpUrl

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokemonSelected: (String) -> Unit
) {
    val pokemons = viewModel.state.collectAsState().value.pokemons.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(pokemons.itemCount) { index ->
            val pokemon = pokemons[index]!!
            Row(modifier = Modifier
                .fillParentMaxWidth()
                .clickable {
                    //TODO: Move this logic to the API layer
                    val id = pokemon.url.toHttpUrl().encodedPathSegments.run { get(count() - 2) }
                    onPokemonSelected(id)
                }
                .padding(10.dp)
            ) {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        pokemons.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = pokemons.loadState.refresh as LoadState.Error
                    item {
                        Text(
                            modifier = Modifier.fillParentMaxSize(),
                            text = error.error.localizedMessage!!
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.Error -> {
                    val error = pokemons.loadState.append as LoadState.Error
                    item {
                        Text(
                            modifier = Modifier.fillParentMaxSize(),
                            text = error.error.localizedMessage!!
                        )
                    }
                }
            }
        }
    }
}