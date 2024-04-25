package com.nightfury.moviedb.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.nightfury.moviedb.BuildConfig
import com.nightfury.moviedb.data.api.DiscoverApi
import com.nightfury.moviedb.data.models.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource
@Inject
constructor(
    private val api: DiscoverApi,
    private val apiKey: String = BuildConfig.API_KEY
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val movieList: List<Movie> =
                try {
                    val response = api.getAllMovies(apiKey, page)
                    response.body()?.results ?: emptyList()
                } catch (e: Exception) {
                    e.printStackTrace()
                    emptyList()
                }

            Page(
                data = movieList,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }


}