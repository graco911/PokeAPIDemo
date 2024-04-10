package com.gracodev.data.repository

import com.gracodev.data.database.PokemonRoomDataSource
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.model.pokemondata.PokemonInformationResponse
import com.gracodev.data.model.pokemondata.Types
import com.gracodev.data.remote.PokeAPIDataSource
import com.gracodev.data.usecaseresult.UseCaseResult

class PokemonRepository(
    private val pokeAPIDataSource: PokeAPIDataSource,
    private val pokemonRoomDataSource: PokemonRoomDataSource
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
                                    0, 0, 0, "", 0, "",
                                    0, ""
                                )
                            )
                        }

                        is UseCaseResult.Success -> {
                            result.add(PokemonMapper.mapToPokemon(pokemonInformation.data))
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

    object PokemonMapper {
        fun mapToPokemon(response: PokemonInformationResponse): PokemonInformation {
            return PokemonInformation(
                dbId = 0,
                height = response.height,
                id = response.id,
                name = response.name,
                order = response.order,
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${response.id}.png",
                type = response.types.first().type?.name!! ?: "",
                weight = response.weight
            )
        }
    }
}