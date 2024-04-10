package com.gracodev.domain.usecase

import com.gracodev.data.repository.PokemonPagingMediatorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchPokemonPagingListUseCase(
    private val pokemonPagingMediatorRepository: PokemonPagingMediatorRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke() = withContext(defaultDispatcher) {
        pokemonPagingMediatorRepository.getPokemonPagingFromMediator()
    }
}