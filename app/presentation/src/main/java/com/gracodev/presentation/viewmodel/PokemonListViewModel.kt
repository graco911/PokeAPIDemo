package com.gracodev.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.domain.usecase.FetchPokemonListUseCase
import com.gracodev.presentation.states.UIStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val fetchPokemonListUseCase: FetchPokemonListUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<UIStates<List<PokemonInformation>>>(UIStates.Init)
    val uiState: StateFlow<UIStates<List<PokemonInformation>>> = _uiState.asStateFlow()

    fun fetchPokemonData() {
        viewModelScope.launch {
            try {
                _uiState.value = UIStates.Loading

                val result = fetchPokemonListUseCase.invoke(0, 25)
                _uiState.value = result.toUIStates()

            } catch (ex: Exception) {
                _uiState.value = UIStates.Error(ex.message ?: "Error desconocido")
            }
        }
    }
}