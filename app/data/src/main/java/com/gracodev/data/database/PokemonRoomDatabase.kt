package com.gracodev.data.database

import androidx.paging.PagingSource
import com.gracodev.data.dao.PokemonDAO
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.usecaseresult.UseCaseResult

class PokemonRoomDatabase(private val pokemonDAO: PokemonDAO) : IPokemonRoom {
    override suspend fun fetchPokemonList():
            UseCaseResult<List<PokemonInformation>> {
        return UseCaseResult.Success(pokemonDAO.getAllPokemons())
    }

    override suspend fun fetchPokemonPagingList():
            PagingSource<Int, PokemonInformation> {
        return pokemonDAO.getPagingPokemons()
    }

    override suspend fun deleteDatabase(): UseCaseResult<Unit> {
        return UseCaseResult.Success(pokemonDAO.deleteAll())
    }

    override suspend fun insertData(pokemonData: PokemonInformation): UseCaseResult<Long> {
        return UseCaseResult.Success(pokemonDAO.insert(pokemonData))
    }
}