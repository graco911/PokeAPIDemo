package com.gracodev.domain.usecase

import com.gracodev.data.repository.PokemonPagingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchPokemonPagingListUseCase(
    private val pokemonPagingRepository: PokemonPagingRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke() = withContext(defaultDispatcher) {
        pokemonPagingRepository.getPokemonPagingSource()
    }
}