package com.gracodev.data.database

import com.gracodev.data.dao.PokemonDAO
import com.gracodev.data.remote.PokemonAPI
import com.gracodev.domain.model.pokemondata.PokemonItem

class PokemonRoomDatabase(private val pokemonDAO: PokemonDAO) : PokemonAPI {
    override suspend fun fetchPokemonList(offset: Int, limit: Int): List<PokemonItem> {
        return pokemonDAO.getAllPokemons()
    }
}