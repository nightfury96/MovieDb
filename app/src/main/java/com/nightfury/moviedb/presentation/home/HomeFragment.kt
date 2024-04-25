package com.nightfury.moviedb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightfury.moviedb.R
import com.nightfury.moviedb.databinding.FragmentHomeBinding
import com.nightfury.moviedb.presentation.home.adapter.MovieRVAdapter
import com.nightfury.moviedb.presentation.util.DateUtil
import com.nightfury.moviedb.presentation.util.gone
import com.nightfury.moviedb.presentation.util.hideKeyboard
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
        initMenu()
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.searchItemMenu)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String): Boolean {
                        return true
                    }

                    override fun onQueryTextSubmit(query: String): Boolean {
                        homeViewModel.getSearchedMovie(query)
                        hideKeyboard(searchView)
                        return true
                    }
                })
                searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                        if (homeViewModel.isSearchMode) {
                            homeViewModel.getAllMovies(DateUtil.getPrevMonthDate())
                        }
                        hideKeyboard(searchView)
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.searchItemMenu -> {
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun hideKeyboard(searchView: SearchView) {
        searchView.clearFocus()
        activity?.hideKeyboard()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getAllMovies(DateUtil.getPrevMonthDate())
    }

    private fun bindRV() {
        if (homeViewModel.isSearchMode) {
            homeViewModel.getAllMovies(DateUtil.getPrevMonthDate())
        }
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