package com.nightfury.moviedb.presentation.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nightfury.moviedb.data.models.Movie

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}