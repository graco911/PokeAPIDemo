package com.gracodev.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gracodev.domain.model.pokemondata.PokemonItem

@Dao
interface PokemonDAO {
    @Insert
    suspend fun insert(pokemonItem: PokemonItem)

    @Query("SELECT * FROM pokemon_list")
    suspend fun getAllPokemons(): List<PokemonItem>

    @Query("DELETE FROM pokemon_list")
    suspend fun deleteAll()
}