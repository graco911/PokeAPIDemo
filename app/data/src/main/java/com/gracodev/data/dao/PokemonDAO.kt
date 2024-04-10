package com.gracodev.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gracodev.data.model.pokemondata.PokemonInformation

@Dao
interface PokemonDAO {
    @Insert
    suspend fun insert(pokemonItem: PokemonInformation): Long

    @Query("SELECT * FROM pokemon_list ORDER BY dbId")
    fun getPagingPokemons(): PagingSource<Int, PokemonInformation>

    @Query("SELECT * FROM pokemon_list")
    suspend fun getAllPokemons(): List<PokemonInformation>

    @Query("DELETE FROM pokemon_list")
    suspend fun deleteAll()
}