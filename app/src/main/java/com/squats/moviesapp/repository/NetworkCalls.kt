package com.parshwahotel.parshwahotelapp.repository

import android.app.Application
import com.parshwahotel.parshwahotelapp.repository.retrofit.RetrofitClient
import com.squats.moviesapp.screens.model.MovieDetails
import com.squats.moviesapp.screens.model.MoviesListResponseModel


object NetworkCalls {

    suspend fun getList(map:HashMap<String,String>,application: Application): MoviesListResponseModel
    {
        return RetrofitClient.getRetrofitClient(application).getMoviesByGenre(map)
    }

    suspend fun getMovie(map: HashMap<String, String>, mapplication: Application): MovieDetails? {
        return RetrofitClient.getRetrofitClient(mapplication).getMovieDetails(map)

    }
}