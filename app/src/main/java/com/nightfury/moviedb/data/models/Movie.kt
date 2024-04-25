package com.nightfury.moviedb.data.models

//@Entity(primaryKeys = [("id")])
data class Movie(
    var page: Int,
    val poster_path: String?,
    val overview: String,
    val id: Int,
    val title: String,
)