package com.gracodev.data.remote

import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.data.model.pokemondata.PokemonListResponse
import com.gracodev.data.usecaseresult.UseCaseResult
import kotlinx.coroutines.Deferred
import retrofit2.Response

class RetrofitPokeAPI(private val pokeAPI: PokeAPI) : IPokemonAPI {

    private suspend fun <T : Any> executeRequest(apiCall: suspend () -> Deferred<Response<T>>): UseCaseResult<T> {
        return try {
            val response = apiCall.invoke().await()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    UseCaseResult.Success(body)
                } else {
                    UseCaseResult.Error(Exception("Response body is null"))
                }
            } else {
                UseCaseResult.Error(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun fetchPokemonList(
        offset: Int,
        limit: Int
    ): UseCaseResult<PokemonListResponse> {
        return executeRequest { pokeAPI.getPokemonList(offset, limit) }
    }

    override suspend fun getPokemonById(id: String): UseCaseResult<PokemonInformation> {
        return executeRequest { pokeAPI.getPokemonById(id) }
    }
}