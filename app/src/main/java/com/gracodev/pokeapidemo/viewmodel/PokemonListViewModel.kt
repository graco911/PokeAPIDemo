package com.gracodev.pokeapidemo.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.domain.usecase.FetchPokemonListUseCase
import com.gracodev.domain.usecase.FetchPokemonPagingListUseCase
import com.gracodev.pokeapidemo.states.UIStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val fetchPokemonListUseCase: FetchPokemonListUseCase,
    private val fetchPokemonPagingListUseCase: FetchPokemonPagingListUseCase
) : BaseViewModel() {

    private val _loadingState = MutableStateFlow<Boolean>(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _uiState = MutableStateFlow<UIStates<List<PokemonInformation>>>(UIStates.Init)
    val uiState: StateFlow<UIStates<List<PokemonInformation>>> = _uiState.asStateFlow()

    private val _pagingSource = MutableStateFlow<PagingSource<Int, PokemonInformation>?>(null)
    val pagingData: StateFlow<PagingData<PokemonInformation>> = _pagingSource
        .flatMapLatest { pagingSource ->
            pagingSource?.let {
                Pager(config = PagingConfig(pageSize = 25)) {
                    it
                }.flow.cachedIn(viewModelScope)
            } ?: flow { emit(PagingData.empty()) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())


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

    fun fetchPokemonPagingData() {
        viewModelScope.launch {
            _loadingState.value = true
            _pagingSource.value = fetchPokemonPagingListUseCase()
            _loadingState.value = false
        }
    }
}