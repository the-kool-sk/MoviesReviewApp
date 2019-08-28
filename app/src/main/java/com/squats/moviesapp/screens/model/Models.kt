package com.squats.moviesapp.screens.model

import android.media.Rating
import android.R.attr.country
import android.R.attr.country




data class RequestModel(val emailId: String, val password: String)

data class MoviesListResponseModel(
    val Search: List<MoviePosterDetailsResponseModel>,
    val totalResults: String,
    val Response: String
)

data class MoviePosterDetailsResponseModel(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)

data class MovieListResponseModel(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Ratings: ArrayList<Ratings>,
    val Metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val DVD: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: String
)
data class Ratings(val source: String, val value: String)

data class ParentMovieList(var genreTitle:String,var moviesListResponseModel :MoviesListResponseModel?)

data class MovieDetails(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Metascore: String,
    val imdbRating: Float,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val DVD: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: String
)
