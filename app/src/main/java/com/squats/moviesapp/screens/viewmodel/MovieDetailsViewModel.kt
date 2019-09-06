package com.squats.moviesapp.screens.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.squats.moviesapp.repository.Repository
import com.squats.moviesapp.screens.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(val mapplication: Application) : AndroidViewModel(mapplication) {

    val movieDetailsLiveData=MutableLiveData<MovieDetails>()

    fun getMovieDetails(movieID:String?){
        val map: HashMap<String, String> = HashMap()
        map["type"] = "movie"
        map["apikey"] = "c0617fee"
        map["i"] = movieID!!
        var movieDetails:MovieDetails?=null
        viewModelScope.launch(Dispatchers.IO) {
                movieDetails= Repository.fetchMovieDetails(map,mapplication)
                movieDetailsLiveData.postValue(movieDetails)

        }
    }
}
