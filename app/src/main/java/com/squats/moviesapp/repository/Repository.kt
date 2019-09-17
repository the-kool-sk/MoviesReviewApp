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

    suspend fun fetchList(map: HashMap<String, String>,
                          mApplication: Application): MoviesListResponseModel? {
        var moviesListResponseModel: MoviesListResponseModel? = null

        try {
            moviesListResponseModel = NetworkCalls.getList(map, mApplication)
        } catch (e: Throwable) {
            handleNetworkError(e, mApplication)
            GlobalScope.launch(Dispatchers.Main) {
                mApplication.toast(e.message.toString())
            }
        }
        return moviesListResponseModel
    }

    suspend fun fetchMovieDetails(map: HashMap<String, String>,
                                  mApplication: Application): MovieDetails? {
        var moviesDetails: MovieDetails? = null
        try {
            moviesDetails = NetworkCalls.getMovie(map, mApplication)
        } catch (e: Throwable) {
            handleNetworkError(e, mApplication)
            GlobalScope.launch(Dispatchers.Main) {
                mApplication.toast(e.message.toString())
            }
        }
        return moviesDetails
    }
}