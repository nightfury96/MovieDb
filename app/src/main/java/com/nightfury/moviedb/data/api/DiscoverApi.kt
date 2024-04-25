package com.nightfury.moviedb.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {

    @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int? = null,
        @Query("release_date.gte") date: String,
    ): Response<MoviesResponse>

    @GET("/3/search/movie")
    suspend fun getSearchedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int? = null,
        @Query("query") query: String
    ): Response<MoviesResponse>
}