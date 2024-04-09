package com.gracodev.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.presentation.databinding.PokemonItemBinding
import com.gracodev.presentation.viewholders.PokemonViewHolder

class PokemonPagingAdapter(
    private val onItemClick: (PokemonInformation) -> Unit
) :
    PagingDataAdapter<PokemonInformation, PokemonViewHolder>(PokemonComparator()) {
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        pokemon?.let {
            holder.bind(pokemon) { item ->
                onItemClick(item)
            }
        }
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

    class PokemonComparator : DiffUtil.ItemCallback<PokemonInformation>() {
        override fun areItemsTheSame(
            oldItem: PokemonInformation,
            newItem: PokemonInformation
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PokemonInformation,
            newItem: PokemonInformation
        ): Boolean {
            return oldItem == newItem
        }
    }
}