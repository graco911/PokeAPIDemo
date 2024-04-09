package com.gracodev.pokeapidemo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.pokeapidemo.databinding.PokemonItemBinding
import com.gracodev.pokeapidemo.viewholders.PokemonViewHolder

class PokemonListAdapter(
    private val pokemonList: MutableList<PokemonInformation> = mutableListOf(),
    private val onItemClick: (PokemonInformation) -> Unit
) : RecyclerView.Adapter<PokemonViewHolder>() {

    fun submitAll(newPokemonList: MutableList<PokemonInformation>) {
        pokemonList.clear()
        pokemonList.addAll(newPokemonList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        PokemonViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position]) { item ->
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = pokemonList.size
}