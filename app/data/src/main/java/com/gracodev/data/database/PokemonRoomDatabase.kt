package com.gracodev.data.database

import com.gracodev.data.dao.PokemonDAO
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.usecaseresult.UseCaseResult

class PokemonRoomDatabase(private val pokemonDAO: PokemonDAO) : IPokemonRoom {
    override suspend fun fetchPokemonList(): UseCaseResult<List<PokemonInformation>> {
        return UseCaseResult.Success(pokemonDAO.getAllPokemons())
    }
}