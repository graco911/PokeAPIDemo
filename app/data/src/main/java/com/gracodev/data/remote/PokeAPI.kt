package com.gracodev.data.remote

import com.gracodev.domain.model.pokemondata.PokemonItem
import com.gracodev.domain.model.pokemondata.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {

    /**
     * Define endpoint to get a pokemon list using pagination
     * */
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse

    /**
     * Define endpoint to get a pokemon data using your Pokemon ID
     * */
    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ): PokemonItem
}