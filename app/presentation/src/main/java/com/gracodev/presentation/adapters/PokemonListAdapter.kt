package com.gracodev.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.presentation.R
import com.gracodev.presentation.databinding.PokemonItemBinding

class PokemonListAdapter(
    private val pokemonList: MutableList<PokemonInformation> = mutableListOf(),
    private val onItemClick: (PokemonInformation) -> Unit
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    fun submitAll(newPokemonList: MutableList<PokemonInformation>) {
        pokemonList.clear()
        pokemonList.addAll(newPokemonList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        ViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PokemonListAdapter.ViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size

    inner class ViewHolder(private val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonInformation) {
            binding.apply {
                Glide
                    .with(binding.root.context)
                    .load(pokemon.image)
                    .centerCrop()
                    .placeholder(R.drawable.pokeball)
                    .into(pokemonImageView);
            }
            binding.root.setOnClickListener { onItemClick(pokemon) }
        }
    }
}