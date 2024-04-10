package com.gracodev.pokeapidemo.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.pokeapidemo.R
import com.gracodev.pokeapidemo.databinding.FragmentPokemonDetailBinding
import com.gracodev.pokeapidemo.databinding.ItemContainerLayoutBinding
import com.gracodev.pokeapidemo.utils.PokemonTypeUtils

class PokemonDetailFragment : BaseFragment() {

    override var TAG: String = this.javaClass.name

    private val binding: FragmentPokemonDetailBinding by lazy {
        FragmentPokemonDetailBinding.inflate(layoutInflater)
    }

    private lateinit var pokemon: PokemonInformation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractBundleInfo()
        setPokemonData()
    }

    private fun setPokemonData() {
        binding.apply {
            pokemonName.text = pokemon.name
            setDataContainer(pokemonIdContainer1, "Weight", pokemon.weight.toString())
            setDataContainer(pokemonIdContainer2, "Height", pokemon.height.toString())
            pokemon.types.first().type?.name.let {
                setDataContainer(
                    pokemonIdContainer3, "Type",
                    it ?: ""
                )
            }
            setDataContainer(pokemonIdContainer4, "ID", pokemon.id.toString())
            Glide
                .with(requireContext())
                .load(pokemon.image)
                .centerCrop()
                .placeholder(R.drawable.pokeball)
                .into(pokemonImage);

            val color = ContextCompat.getColor(
                requireContext(),
                PokemonTypeUtils.getTypeColor(pokemon.types.first().type?.name ?: "")
            )

            val drawable = DrawableCompat.wrap(backgroundCardType.drawable).mutate()
            DrawableCompat.setTint(
                drawable, color
            )

            ViewCompat
                .setBackgroundTintList(
                    backgroundCardType, ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            PokemonTypeUtils.getTypeColor(
                                pokemon.types.first().type?.name ?: ""
                            )
                        )
                    )
                )
        }
    }

    private fun setDataContainer(
        pokemonIdContainer: ItemContainerLayoutBinding,
        title: String,
        item: String
    ) {
        pokemonIdContainer.apply {
            propertyTitle.text = title
            propertyItem.text = item
        }
    }

    private fun extractBundleInfo() {
        arguments?.getParcelable<PokemonInformation>("POKEMON_DATA").let {
            pokemon = it!!
        }
    }
}