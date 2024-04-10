package com.gracodev.domain.usecase

import com.gracodev.data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchPokemonListUseCase(
    private val pokemonRepository: PokemonRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(offSet: Int, limit: Int) = withContext(defaultDispatcher) {
        pokemonRepository.fetchPokemonList(offSet, limit)
    }
}