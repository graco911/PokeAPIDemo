package com.gracodev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gracodev.data.dao.PokemonDAO
import com.gracodev.domain.model.pokemondata.PokemonItem

@Database(entities = [PokemonItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDAO(): PokemonDAO
}