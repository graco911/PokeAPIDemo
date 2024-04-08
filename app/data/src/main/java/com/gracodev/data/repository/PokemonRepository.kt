package com.gracodev.data.repository

import com.gracodev.data.database.PokemonRoomDataSource
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.remote.PokeAPIDataSource
import com.gracodev.data.usecaseresult.UseCaseResult

class PokemonRepository(
    private val pokeAPIDataSource: PokeAPIDataSource
) {
    suspend fun fetchPokemonList(offset: Int, limit: Int): UseCaseResult<List<PokemonInformation>> {

        return when (val response = pokeAPIDataSource.fetchPokemonsList(offset, limit)) {
            is UseCaseResult.Error -> {
                return fetchPokemonListOffline()
            }

            is UseCaseResult.Success -> {
                val result: MutableList<PokemonInformation> = mutableListOf()
                for (pokemon in response.data.results) {
                    when (val pokemonInformation =
                        pokeAPIDataSource.getPokemonById(pokemon.getPokemonId())) {
                        is UseCaseResult.Error -> {
                            result.add(
                                PokemonInformation(
                                    0, 0, "", 0, "", 0
                                )
                            )
                        }

                        is UseCaseResult.Success -> {
                            pokemonInformation.data.image =
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                                        "pokemon/other/official-artwork/${pokemon.getPokemonId()}.png"
                            result.add(pokemonInformation.data)
                        }
                    }
                }
                return UseCaseResult.Success(result)
            }
        }
    }

    private suspend fun fetchPokemonListOffline(): UseCaseResult<List<PokemonInformation>> {
        TODO()
    }
}