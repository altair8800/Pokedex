package com.barco.pokedex.ui

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<S : Any>(initialState: S) : ViewModel() {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)

    val state: MutableStateFlow<S> get() = _state

    @MainThread
    protected fun setState(reducer: S.() -> S) {
        val currentState = _state.value
        val newState = currentState.reducer()
        if (newState != currentState) {
            _state.value = newState
        }
    }
}
