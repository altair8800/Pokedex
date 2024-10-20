package com.barco.pokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barco.pokedex.ui.pokemondetails.PokemonDetailsScreen
import com.barco.pokedex.ui.pokemonlist.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val isInDetailView = remember { mutableStateOf(false) }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            title = { Text(text = "Pokemon") },
                            navigationIcon = {
                                if (isInDetailView.value) {
                                    BackButton {
                                        navController.popBackStack()
                                    }
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "list",
                        modifier = Modifier.padding(paddingValues),
                        builder = {
                            composable("list") {
                                isInDetailView.value = false
                                PokemonListScreen { pokemonId ->
                                    navController.navigate("pokemon/$pokemonId")
                                }
                            }
                            composable(
                                "pokemon/{pokemonId}"
                            ) { navBackStackEntry ->
                                isInDetailView.value = true
                                navBackStackEntry.arguments?.getString("pokemonId")?.let {
                                    PokemonDetailsScreen(it)
                                }
                            }
                        })
                }
            }
        }
    }

    @Composable
    private fun BackButton(onBackPressed: () -> Unit) {
        IconButton(onClick = { onBackPressed() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}