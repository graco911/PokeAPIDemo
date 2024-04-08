package com.gracodev.data.remote

import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.model.pokemondata.PokemonListResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
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
    ): Deferred<Response<PokemonListResponse>>

    /**
     * Define endpoint to get a pokemon data using your Pokemon ID
     * */
    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: String
    ): Deferred<Response<PokemonInformation>>
}