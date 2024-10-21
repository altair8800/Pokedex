package com.barco.pokedex.ui.pokemondetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.barco.pokedex.model.PokemonDetails

@Composable
fun PokemonDetailsScreen(id: String, viewModel: PokemonDetailsViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getPokemon(id)
    }
    val state = viewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        state.value.details?.let { article ->
            PokemonDetail(article)
        } ?: CircularProgressIndicator()
    }
}

@Composable
private fun PokemonDetail(pokemonDetails: PokemonDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeadingSection(pokemonDetails)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun HeadingSection(
    pokemonDetails: PokemonDetails,
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .zIndex(1F)
    ) {
        GlideImage(
            modifier = Modifier.fillMaxWidth(),
            model = pokemonDetails.sprites.frontDefault,
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
    }
}