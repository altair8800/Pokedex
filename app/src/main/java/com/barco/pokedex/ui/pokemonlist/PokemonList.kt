package com.barco.pokedex.ui.pokemonlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.barco.pokedex.model.PokemonOverview
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
                    text = pokemon.name.replaceFirstChar { it.uppercaseChar() }, //TODO: Move to ViewModel and use utils package
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        displayOtherPagingStates(pokemons)
    }
}

private fun LazyListScope.displayOtherPagingStates(pokemons: LazyPagingItems<PokemonOverview>) {
    pokemons.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            loadState.refresh is LoadState.Error -> {
                val error = pokemons.loadState.refresh as LoadState.Error
                displayPagingError(error)
            }

            loadState.append is LoadState.Loading -> {
                item { CircularProgressIndicator() }
            }

            loadState.append is LoadState.Error -> {
                val error = pokemons.loadState.append as LoadState.Error
                displayPagingError(error)
            }
        }
    }
}

private fun LazyListScope.displayPagingError(error: LoadState.Error) {
    item {
        Text(
            modifier = Modifier.fillParentMaxSize(),
            text = error.error.localizedMessage!!
        )
    }
}