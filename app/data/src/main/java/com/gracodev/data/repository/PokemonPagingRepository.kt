package com.gracodev.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gracodev.data.dao.PokemonDAO
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.remote.PokeAPIDataSource
import com.gracodev.data.usecaseresult.UseCaseResult

class PokemonPagingRepository(
    private val pokeAPIDataSource: PokeAPIDataSource,
    private val pokemonRoomDataSource: PokemonDAO
) {
    suspend fun getPokemonPagingFromRoom(): PagingSource<Int, PokemonInformation> {
        return pokemonRoomDataSource.getPagingPokemons()
    }

    suspend fun getPokemonPagingSourceFromAPI(): PagingSource<Int, PokemonInformation> {
        return object : PagingSource<Int, PokemonInformation>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonInformation> {
                val offset = params.key ?: 0
                val limit = params.loadSize
                return try {
                    val response = pokeAPIDataSource.fetchPokemonsList(offset, limit)
                    if (response is UseCaseResult.Success) {
                        val pokemonList: MutableList<PokemonInformation> = mutableListOf()
                        for (pokemon in response.data.results) {
                            when (val pokemonInformation =
                                pokeAPIDataSource.getPokemonById(pokemon.getPokemonId())) {
                                is UseCaseResult.Error -> {
                                    pokemonList.add(
                                        PokemonInformation(
                                            0, 0, 0, "", 0, "",
                                            0, ""
                                        )
                                    )
                                }

                                is UseCaseResult.Success -> {
                                    pokemonList.add(
                                        PokemonRepository.PokemonMapper.mapToPokemon(
                                            pokemonInformation.data
                                        )
                                    )
                                }
                            }
                        }

                        pokemonRoomDataSource.deleteAll()

                        for (pokemon in pokemonList) {
                            val pokemonInserted = pokemonRoomDataSource.insert(pokemon)
                            Log.d("Room", "Pokemon inserted with ID: $pokemonInserted")
                        }

                        LoadResult.Page(
                            data = pokemonList,
                            prevKey = if (offset == 0) null else offset - limit,
                            nextKey = offset + limit
                        )
                    } else {
                        LoadResult.Error(Exception("Failed to fetch pokemon list"))
                    }
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, PokemonInformation>): Int? {
                return null
            }
        }
    }
}