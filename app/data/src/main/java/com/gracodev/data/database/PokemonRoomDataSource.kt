package com.gracodev.data.database

import androidx.paging.PagingSource
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

    suspend fun fetchPokemonPagingList(): PagingSource<Int, PokemonInformation> =
        withContext(ioDispatcher) {
            iPokemonRoom.fetchPokemonPagingList()
        }

    suspend fun deleteDatabase(): UseCaseResult<Unit> =
        withContext(ioDispatcher) {
            try {
                iPokemonRoom.deleteDatabase()
            } catch (ex: Exception) {
                UseCaseResult.Error(ex)
            }
        }

    suspend fun insertData(pokemonData: PokemonInformation): UseCaseResult<Long> =
        withContext(ioDispatcher) {
            try {
                iPokemonRoom.insertData(pokemonData)
            } catch (ex: Exception) {
                UseCaseResult.Error(ex)
            }
        }
}

interface IPokemonRoom {
    suspend fun fetchPokemonList(): UseCaseResult<List<PokemonInformation>>
    suspend fun fetchPokemonPagingList(): PagingSource<Int, PokemonInformation>
    suspend fun deleteDatabase(): UseCaseResult<Unit>
    suspend fun insertData(pokemonData: PokemonInformation): UseCaseResult<Long>
}