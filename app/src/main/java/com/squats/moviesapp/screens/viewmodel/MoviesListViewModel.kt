package com.squats.moviesapp.screens.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.squats.moviesapp.repository.Repository
import com.squats.moviesapp.screens.model.ParentMovieList
import kotlinx.coroutines.launch

class MoviesListViewModel(private val mapplication: Application) : AndroidViewModel(mapplication) {
    var parentMovieListliveData: MutableLiveData<ArrayList<ParentMovieList>> = MutableLiveData()
    var isloading: MutableLiveData<Boolean> = MutableLiveData()

    fun getMoviesByGener() {
        isloading.postValue(true) //Set true to show Progress Bar till fetching data
        val map: HashMap<String, String> = HashMap()
        val parentMovieList: ArrayList<ParentMovieList> = ArrayList()
        val geners =
            arrayOf("Animated", "Comedy", "Action", "Horror", "Adventure", "Spy", "Fantasy", "War")
        map["type"] = "movie"
        map["apikey"] = "c0617fee"

        viewModelScope.launch {
            for (gener in geners) {
                map["s"] = gener
                val moviesListResponseModel = Repository.fetchList(map, mapplication)
                parentMovieList.add(ParentMovieList(gener, moviesListResponseModel))
            }
            isloading.postValue(false) // Hide Progress Bar after data is fetched
            parentMovieListliveData.postValue(parentMovieList)
        }
    }
}
