package com.gracodev.pokeapidemo.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gracodev.domain.usecase.FetchPokemonListUseCase
import com.gracodev.domain.usecase.FetchPokemonPagingListUseCase
import com.gracodev.pokeapidemo.viewmodel.PokemonListViewModel

class PokemonViewModelFactory(
    private val fetchPokemonListUseCase: FetchPokemonListUseCase,
    private val fetchPokemonPagingListUseCase: FetchPokemonPagingListUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonListViewModel(fetchPokemonListUseCase, fetchPokemonPagingListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}