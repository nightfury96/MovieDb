package com.nightfury.moviedb.presentation.home

import android.util.Log
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
    fun getAllMovies(date: String) {
        Log.d("HomeViewModel", "loading movies withing last month $date")
        viewModelScope.launch {
            _movies.emit(PagingData.empty())
            repository
                .getAllMovies(date)
                .cachedIn(viewModelScope)
                .collect { _movies.value = it }
        }
    }

    fun getSearchedMovie(query: String) {
        viewModelScope.launch {
            _movies.emit(PagingData.empty())
            repository
                .getSearchedMovies(query)
                .cachedIn(viewModelScope)
                .collect { _movies.value = it }
        }
    }
}