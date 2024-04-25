package com.nightfury.moviedb.data.api

import com.nightfury.moviedb.data.models.Movie

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int)
