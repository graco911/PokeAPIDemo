package com.gracodev.data.model.pokemondata

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable

@Parcelize
data class Types(

    @SerializedName("slot") var slot: Int? = null,
    @SerializedName("type") var type: Type? = Type()

) : Parcelable

@Parcelize
data class Type(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

) : Parcelable
