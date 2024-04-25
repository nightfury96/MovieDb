package com.nightfury.moviedb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightfury.moviedb.databinding.FragmentHomeBinding
import com.nightfury.moviedb.presentation.home.adapter.MovieRVAdapter
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