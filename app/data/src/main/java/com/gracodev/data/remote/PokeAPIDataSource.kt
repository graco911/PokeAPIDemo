package com.gracodev.data.remote

import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.model.pokemondata.PokemonListResponse
import com.gracodev.data.usecaseresult.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PokeAPIDataSource(
    private val iPokemonAPI: IPokemonAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemonsList(offset: Int, limit: Int): UseCaseResult<PokemonListResponse> =
        withContext(ioDispatcher) {
            iPokemonAPI.fetchPokemonList(offset, limit)
        }

    suspend fun getPokemonById(id: String): UseCaseResult<PokemonInformation> =
        withContext(ioDispatcher) {
            iPokemonAPI.getPokemonById(id)
        }
}

interface IPokemonAPI {
    suspend fun fetchPokemonList(offset: Int, limit: Int): UseCaseResult<PokemonListResponse>
    suspend fun getPokemonById(id: String): UseCaseResult<PokemonInformation>
}