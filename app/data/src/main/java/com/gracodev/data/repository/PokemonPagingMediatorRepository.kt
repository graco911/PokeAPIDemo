package com.gracodev.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.gracodev.data.model.pokemondata.PokemonInformation

class PokemonPagingMediatorRepository(
    private val pokemonPagingRepository: PokemonPagingRepository
) {
    suspend fun getPokemonPagingFromMediator(): PagingSource<Int, PokemonInformation> {
        val remoteSource = pokemonPagingRepository.getPokemonPagingSourceFromAPI()
        val localSource = pokemonPagingRepository.getPokemonPagingFromRoom()

        if (remoteSource is PagingSource.LoadResult.Error<*, *>) {
            Log.d("Mediator", "Return Remotesource")
            return localSource
        } else {
            Log.d("Mediator", "Return Localsource")
            return remoteSource
        }
    }
}