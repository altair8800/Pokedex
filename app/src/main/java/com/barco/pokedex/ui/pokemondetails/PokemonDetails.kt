package com.barco.pokedex.ui.pokemondetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.barco.pokedex.model.PokemonDetails
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun PokemonDetailsScreen(id: String, viewModel: PokemonDetailsViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getPokemon(id) //TODO: Use @AssistedInject OR Molecule library to inject the id at instantiation
    }
    val state = viewModel.state.collectAsState().value

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.refreshing) {
            CircularProgressIndicator()
        } else {
            state.details?.let { details ->
                PokemonDetail(details)
            }
        }
    }
}

@Composable
private fun PokemonDetail(pokemonDetails: PokemonDetails) {
    OutlinedCard (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeadingSection(pokemonDetails)
            Text(
                text = pokemonDetails.name.replaceFirstChar { it.uppercaseChar() },
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Height: ${pokemonDetails.height}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun HeadingSection(
    pokemonDetails: PokemonDetails,
) {
    Box(
        modifier = Modifier.size(200.dp)
    ) {
        GlideImage(
            model = pokemonDetails.sprites.frontDefault,
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
    }
}