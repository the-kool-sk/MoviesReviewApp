package com.squats.moviesapp.screens.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.parshwahotel.parshwahotelapp.repository.Repository
import com.squats.moviesapp.R
import com.squats.moviesapp.screens.model.MoviePosterDetailsResponseModel
import com.squats.moviesapp.screens.model.ParentMovieList
import kotlinx.coroutines.launch

class MoviesListViewModel(val mapplication: Application) : AndroidViewModel(mapplication) {
    var parentMovieListliveData: MutableLiveData<ArrayList<ParentMovieList>> = MutableLiveData()

    fun getMoviesByGener() {
        val map: HashMap<String, String> = HashMap()
        val parentMovieList: ArrayList<ParentMovieList> = ArrayList()
        val geners = arrayOf("Animated", "Comedy", "Action", "Horror","Adventure","Spy","Fantasy","War")
        map.put("type", "movie")
        map.put("apikey", "c0617fee")

        viewModelScope.launch {
            for (gener in geners) {
                map.put("s", gener)
                val moviesListResponseModel = Repository.fetchList(map, mapplication)
                parentMovieList.add(ParentMovieList(gener, moviesListResponseModel))
            }
            parentMovieListliveData.postValue(parentMovieList)
        }
    }
}
