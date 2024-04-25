package com.nightfury.moviedb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nightfury.moviedb.data.models.Movie
import com.nightfury.moviedb.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private var _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies get() = _movies

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            repository
                .getAllMovies()
                .cachedIn(viewModelScope)
                .collect { _movies.value = it }
        }
    }
}