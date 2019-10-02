package com.squats.moviesapp.screens.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.squats.moviesapp.repository.Repository
import com.squats.moviesapp.screens.model.MoviePosterDetailsResponseModel
import com.squats.moviesapp.screens.model.ParentMovieList
import kotlinx.coroutines.launch

class MoviesListViewModel(private val mapplication: Application) : AndroidViewModel(mapplication) {
    var parentMovieListliveData: MutableLiveData<ArrayList<MoviePosterDetailsResponseModel>> =
        MutableLiveData()
    var isloading: MutableLiveData<Boolean> = MutableLiveData()

    fun getMoviesByGener() {
        isloading.postValue(true) //Set true to show Progress Bar till fetching data
        val map: HashMap<String, Any> = HashMap()
        val parentMovieList: ArrayList<MoviePosterDetailsResponseModel> = ArrayList()
        map["s"] = "Animated"
        map["type"] = "movie"
        map["apikey"] = "c0617fee"
        map["page"] = 1

        viewModelScope.launch {

            val moviesListResponseModel = Repository.fetchList(map, mapplication)
            for (i in 0 until moviesListResponseModel?.Search?.size!!) {
                parentMovieList.add(moviesListResponseModel.Search[i])
            }

            isloading.postValue(false) // Hide Progress Bar after data is fetched
            parentMovieListliveData.postValue(parentMovieList)
        }
    }
}
