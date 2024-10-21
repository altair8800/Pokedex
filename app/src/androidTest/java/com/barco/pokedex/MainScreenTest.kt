package com.barco.pokedex

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.barco.pokedex.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun displaysCorrectPokemonWhenSelectedFromList() { //TODO: Supply mock API responses with WireMock OR use DI to inject test repository
        composeTestRule.waitUntilAtLeastOneExists(hasText("Charmander"), timeoutMillis = 5000)
        composeTestRule.onNodeWithText("Charmander").performClick()
        composeTestRule.waitUntilAtLeastOneExists(hasText("Height: 6"), timeoutMillis = 5000)
    }
}