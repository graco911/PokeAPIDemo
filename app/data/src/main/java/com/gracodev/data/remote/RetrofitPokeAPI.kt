package com.gracodev.data.remote

import com.gracodev.domain.model.pokemondata.PokemonItem

class RetrofitPokeAPI(private val pokeAPI: PokeAPI) : PokemonAPI {
    override suspend fun fetchPokemonList(offset: Int, limit: Int): List<PokemonItem> {
        return pokeAPI.getPokemonList(offset, limit).results
    }
}