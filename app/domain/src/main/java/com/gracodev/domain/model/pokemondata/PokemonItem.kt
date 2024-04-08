package com.gracodev.domain.model.pokemondata

import androidx.room.Entity

@Entity(tableName = "pokemon_list")
data class PokemonItem(
    val name: String,
    val url: String
)