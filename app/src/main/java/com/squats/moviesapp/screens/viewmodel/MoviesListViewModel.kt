package com.squats.moviesapp.screens.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.squats.moviesapp.repository.Repository
import com.squats.moviesapp.screens.model.MoviePosterDetailsResponseModel
import kotlinx.coroutines.launch

class MoviesListViewModel(private val mapplication: Application) : AndroidViewModel(mapplication) {
    var parentMovieListliveData: MutableLiveData<ArrayList<MoviePosterDetailsResponseModel>> =
        MutableLiveData()
    var isloading: MutableLiveData<Boolean> = MutableLiveData()
    val parentMovieList: ArrayList<MoviePosterDetailsResponseModel> = ArrayList()

    fun getMoviesByGener(page: Int) {
        isloading.postValue(true) //Set true to show Progress Bar till fetching data
        val map: HashMap<String, Any> = HashMap()

        map["s"] = "Animated"
        map["type"] = "movie"
        map["apikey"] = "c0617fee"
        map["page"] = page

        viewModelScope.launch {

            val moviesListResponseModel = Repository.fetchList(map, mapplication)
            if (moviesListResponseModel != null) {
                for (element in moviesListResponseModel.Search) {
                    parentMovieList.add(element)
                }
                parentMovieListliveData.postValue(parentMovieList)
            }
            isloading.postValue(false) // Hide Progress Bar after data is fetched
        }
    }

    fun clearList() {
        parentMovieList.clear()
        parentMovieListliveData.postValue(parentMovieList)
    }
}
