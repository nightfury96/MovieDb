package com.nightfury.moviedb.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nightfury.moviedb.BuildConfig
import com.nightfury.moviedb.data.models.Movie
import com.nightfury.moviedb.databinding.MovieViewItemBinding
import javax.inject.Inject

class MovieRVAdapter
@Inject
constructor(
) : PagingDataAdapter<Movie, MovieRVAdapter.MovieViewHolder>(
    MovieDiffCallback
) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieViewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class MovieViewHolder(
        private val binding: MovieViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie?) {
            binding.movieTitleTV.text = data?.title
            binding.movieOverviewTV.text = data?.overview

            Glide.with(binding.root)
                .load(BuildConfig.BASE_POSTER_URL + data?.poster_path)
                .fitCenter()
                .into(binding.moviePosterIV)
        }
    }
}