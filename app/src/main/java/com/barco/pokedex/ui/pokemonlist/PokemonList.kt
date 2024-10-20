package com.barco.pokedex.ui.pokemonlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.barco.pokedex.model.PokemonOverview
import okhttp3.HttpUrl.Companion.toHttpUrl

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokemonSelected: (String) -> Unit
) {
    val pokemons = viewModel.state.collectAsState().value.pokemons
    LazyColumn {
        items(items = pokemons) {
            ListItem(it, onPokemonSelected)
        }
    }
}

@Composable
private fun ListItem(item: PokemonOverview, onPokemonSelected: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        val encodedUrl = item.url.toHttpUrl().encodedPathSegments.run { get(count() - 2) }
        onPokemonSelected(encodedUrl)
    }) {
        Text(
            item.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}