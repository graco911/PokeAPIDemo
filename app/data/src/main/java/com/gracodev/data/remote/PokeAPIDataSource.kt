package com.gracodev.data.remote

import com.gracodev.domain.model.pokemondata.PokemonItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PokeAPIDataSource(
    private val pokemonAPI: PokemonAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemonsList(offset: Int, limit: Int): List<PokemonItem> =
        withContext(ioDispatcher) {
            pokemonAPI.fetchPokemonList(offset, limit)
        }
}

interface PokemonAPI {
    suspend fun fetchPokemonList(offset: Int, limit: Int): List<PokemonItem>
}