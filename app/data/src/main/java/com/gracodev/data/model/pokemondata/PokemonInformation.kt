package com.gracodev.data.model.pokemondata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_list")
data class PokemonInformation(
    @PrimaryKey(autoGenerate = true)
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    var image: String,
    val weight: Int
)
