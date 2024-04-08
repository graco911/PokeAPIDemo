package com.gracodev.presentation.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gracodev.domain.usecase.FetchPokemonListUseCase
import com.gracodev.presentation.viewmodel.PokemonListViewModel

class PokemonViewModelFactory(
    private val fetchPokemonListUseCase: FetchPokemonListUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonListViewModel(fetchPokemonListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}