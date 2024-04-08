package com.gracodev.data.model.pokemondata

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon_list")
data class PokemonInformation(
    @PrimaryKey(autoGenerate = true)
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    var image: String,
    val weight: Int,
    var types: ArrayList<Types>
)

data class Types(

    @SerializedName("slot") var slot: Int? = null,
    @SerializedName("type") var type: Type? = Type()

)

data class Type(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)
