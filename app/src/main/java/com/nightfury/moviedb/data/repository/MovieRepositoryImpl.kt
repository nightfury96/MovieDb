package com.nightfury.moviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nightfury.moviedb.data.api.DiscoverApi
import com.nightfury.moviedb.data.models.Movie
import com.nightfury.moviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl
@Inject
constructor(
    private val api: DiscoverApi
) : MovieRepository {
    override fun getAllMovies(): Flow<PagingData<Movie>> = Pager(PagingConfig(1)) {
        MoviePagingSource(api)
    }.flow
}