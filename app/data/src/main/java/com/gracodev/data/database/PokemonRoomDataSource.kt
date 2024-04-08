package com.gracodev.data.database

import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.usecaseresult.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PokemonRoomDataSource(
    private val iPokemonRoom: IPokemonRoom,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemonList(): UseCaseResult<List<PokemonInformation>> =
        withContext(ioDispatcher) {
            try {
                iPokemonRoom.fetchPokemonList()
            } catch (ex: Exception) {
                UseCaseResult.Error(ex)
            }
        }
}

interface IPokemonRoom {
    suspend fun fetchPokemonList(): UseCaseResult<List<PokemonInformation>>
}