package com.gracodev.data.repository

import com.gracodev.data.database.PokemonRoomDataSource
import com.gracodev.data.remote.PokeAPIDataSource
import com.gracodev.domain.model.pokemondata.PokemonItem

class PokeAPIRepository(
    private val pokeAPIDataSource: PokeAPIDataSource,
    private val pokemonRoomDataSource: PokemonRoomDataSource
) {
    suspend fun fetchPokemonList(offset: Int, limit: Int): List<PokemonItem> =
        pokeAPIDataSource.fetchPokemonsList(offset, limit)

    suspend fun fetchPokemonListOffiline(): List<PokemonItem> =
        pokemonRoomDataSource.fetchPokemonList()
}