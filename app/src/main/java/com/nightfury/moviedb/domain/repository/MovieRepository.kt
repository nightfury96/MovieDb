package com.nightfury.moviedb.domain.repository

import androidx.paging.PagingData
import com.nightfury.moviedb.data.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getAllMovies(date: String): Flow<PagingData<Movie>>
    fun getSearchedMovies(query: String): Flow<PagingData<Movie>>
}