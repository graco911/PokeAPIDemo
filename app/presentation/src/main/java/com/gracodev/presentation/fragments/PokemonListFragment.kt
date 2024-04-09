package com.gracodev.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gracodev.data.model.pokemondata.PokemonInformation
import com.gracodev.presentation.adapters.PokemonListAdapter
import com.gracodev.presentation.adapters.PokemonPagingAdapter
import com.gracodev.presentation.databinding.FragmentPokemonListBinding
import com.gracodev.presentation.states.UIStates
import com.gracodev.presentation.viewmodel.PokemonListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PokemonListFragment : BaseFragment() {

    override var TAG: String = this.javaClass.name

    private val viewModel: PokemonListViewModel by activityViewModel()

    private val binding: FragmentPokemonListBinding by lazy {
        FragmentPokemonListBinding.inflate(layoutInflater)
    }

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        binding.swipeRefreshLayout
    }

    private val pokemonListAdapter: PokemonListAdapter by lazy {
        PokemonListAdapter() {
            handleTap(it)
        }
    }

    private val pokemonListPagingAdapter: PokemonPagingAdapter by lazy {
        PokemonPagingAdapter() {
            handleTap(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpObservableUIState()
        setUpSwipeRefreshLayout()
        viewModel.fetchPokemonPagingData()
    }

    private fun setUpObservableUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UIStates.Error -> {
                        }

                        UIStates.Init -> {
                        }

                        UIStates.Loading -> {
                            showDialog(TAG)
                        }

                        is UIStates.Success -> {
                            dismissDialog()
                            swipeRefreshLayout.isRefreshing = false
                            uiState.value?.let { pokemonListAdapter.submitAll(it.toMutableList()) }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingData.collectLatest { pagingData ->
                    dismissDialog()
                    swipeRefreshLayout.isRefreshing = false
                    lifecycleScope.launch {
                        pokemonListPagingAdapter.submitData(pagingData)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingState.collect() { loading ->
                    if (loading) {
                        showDialog(TAG)
                    } else {
                        dismissDialog()
                    }
                }
            }
        }
    }

    private fun setUpSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchPokemonPagingData()
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            recylerViewPokemonsList.adapter = pokemonListPagingAdapter
        }
    }

    private fun handleTap(pokemonTapped: PokemonInformation) {
    }
}