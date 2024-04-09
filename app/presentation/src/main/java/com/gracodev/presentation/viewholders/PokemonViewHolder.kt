package com.gracodev.presentation.viewholders

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.presentation.R
import com.gracodev.presentation.databinding.PokemonItemBinding
import com.gracodev.presentation.utils.PokemonTypeUtils

class PokemonViewHolder(private val binding: PokemonItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: PokemonInformation, click: (PokemonInformation) -> Unit) {
        binding.apply {
            Glide
                .with(binding.root.context)
                .load(pokemon.image)
                .centerCrop()
                .placeholder(R.drawable.pokeball)
                .into(pokemonImageView);


            ViewCompat
                .setBackgroundTintList(
                    backgroundCircleLayout, ColorStateList.valueOf(
                        ContextCompat.getColor(
                            binding.root.context,
                            PokemonTypeUtils.getTypeColor(
                                pokemon.types.first().type?.name ?: ""
                            )
                        )
                    )
                )
        }
        binding.root.setOnClickListener { click(pokemon) }
    }
}