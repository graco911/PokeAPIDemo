package com.gracodev.data.model.pokemondata

import androidx.room.Entity

data class PokemonItem(
    val name: String,
    val url: String
) {
    fun getPokemonId(): String {
        return url.split("/".toRegex()).dropLast(1).last()
    }
}