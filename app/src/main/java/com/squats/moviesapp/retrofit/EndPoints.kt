package com.squats.moviesapp.retrofit

import com.squats.moviesapp.screens.model.MovieDetails
import com.squats.moviesapp.screens.model.MovieListResponseModel
import com.squats.moviesapp.screens.model.MoviesListResponseModel
import com.squats.moviesapp.screens.model.RequestModel
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.QueryMap

interface EndPoints {
/*
    @POST("/genre/movie/list")
    suspend fun getGenreList(request: RequestModel): ArrayList<MovieListResponseModel>*/

    @GET("/?")
    suspend fun getMoviesByGenre(@QueryMap map:HashMap<String,String>): MoviesListResponseModel

    @GET("/?")
    suspend fun getMovieDetails(@QueryMap map:HashMap<String,String>): MovieDetails
}