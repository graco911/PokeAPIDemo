package com.gracodev.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.remote.PokeAPIDataSource
import com.gracodev.data.usecaseresult.UseCaseResult

class PokemonPagingRepository(
    private val pokeAPIDataSource: PokeAPIDataSource
) {
    fun getPokemonPagingSource(): UseCaseResult<PagingSource<Int, PokemonInformation>> {
        return UseCaseResult.Success(object : PagingSource<Int, PokemonInformation>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonInformation> {
                val offset = params.key ?: 0
                val limit = params.loadSize
                return try {
                    val response = pokeAPIDataSource.fetchPokemonsList(offset, limit)
                    if (response is UseCaseResult.Success) {
                        val pokemonList: MutableList<PokemonInformation> = mutableListOf()
                        for (pokemon in response.data.results) {
                            when (val pokemonInformation = pokeAPIDataSource.getPokemonById(pokemon.getPokemonId())) {
                                is UseCaseResult.Error -> {
                                    pokemonList.add(
                                        PokemonInformation(
                                            0, 0, "", 0, "", 0,
                                            arrayListOf()
                                        )
                                    )
                                }
                                is UseCaseResult.Success -> {
                                    pokemonInformation.data.image =
                                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.getPokemonId()}.png"
                                    pokemonList.add(pokemonInformation.data)
                                }
                            }
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
                // Placeholder implementation for refreshing
                return null
            }
        })
    }

    private suspend fun fetchPokemonListOffline(): PagingSource.LoadResult<Int, PokemonInformation> {
        TODO()
    }
}