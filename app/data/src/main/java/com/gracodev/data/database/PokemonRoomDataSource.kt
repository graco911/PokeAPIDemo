package com.gracodev.data.database

import com.gracodev.data.remote.PokemonAPI
import com.gracodev.domain.model.pokemondata.PokemonItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PokemonRoomDataSource(
    private val pokemonAPI: PokemonAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemonList(): List<PokemonItem> =
        withContext(ioDispatcher) {
            pokemonAPI.fetchPokemonList(0, 0)
        }
}