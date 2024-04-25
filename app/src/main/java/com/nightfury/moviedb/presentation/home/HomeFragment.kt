package com.nightfury.moviedb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightfury.moviedb.databinding.FragmentHomeBinding
import com.nightfury.moviedb.presentation.home.adapter.MovieRVAdapter
import com.nightfury.moviedb.presentation.util.gone
import com.nightfury.moviedb.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var movieRVAdapter: MovieRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        bindRV()
    }

    private fun bindRV() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.movies.collectLatest {
                movieRVAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            movieRVAdapter.loadStateFlow.collectLatest { loadStates ->
                val isListEmpty =
                    loadStates.refresh is LoadState.NotLoading && movieRVAdapter.itemCount == 0
                val isLoading = loadStates.source.refresh is LoadState.Loading
                if (isListEmpty) {
                    binding.movieListRV.gone()
                    binding.movieLoadingPB.gone()
                    binding.movieEmptyTV.visible()
                } else if (isLoading) {
                    binding.movieListRV.visible()
                    binding.movieEmptyTV.gone()
                    binding.movieLoadingPB.visible()
                } else {
                    binding.movieListRV.visible()
                    binding.movieEmptyTV.gone()
                    binding.movieLoadingPB.gone()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.movieListRV.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            movieRVAdapter = MovieRVAdapter()
            adapter = movieRVAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}