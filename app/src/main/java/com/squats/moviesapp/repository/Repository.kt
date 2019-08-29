package com.squats.moviesapp.repository

import android.app.Application
import com.squats.moviesapp.extentionfunctions.handleNetworkError
import com.squats.moviesapp.extentionfunctions.toast
import com.squats.moviesapp.screens.model.MovieDetails
import com.squats.moviesapp.screens.model.MoviesListResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object Repository {

    suspend fun fetchList(
        map: HashMap<String, String>,
        application: Application
    ): MoviesListResponseModel? {
        var moviesListResponseModel:MoviesListResponseModel?=null

        try {
            moviesListResponseModel= NetworkCalls.getList(map, application)
        } catch (e: Throwable) {
            handleNetworkError(e, application)
            GlobalScope.launch(Dispatchers.Main) {
                application.toast(e.message.toString())
            }
        }
        return moviesListResponseModel
    }

    suspend fun fetchMovieDetails(map: HashMap<String, String>, mapplication: Application): MovieDetails? {
        var moviesDetails:MovieDetails?=null
        try {
            moviesDetails= NetworkCalls.getMovie(map, mapplication)
        } catch (e: Throwable) {
            handleNetworkError(e, mapplication)
            GlobalScope.launch(Dispatchers.Main) {
                mapplication.toast(e.message.toString())
            }
        }
        return moviesDetails
    }
}