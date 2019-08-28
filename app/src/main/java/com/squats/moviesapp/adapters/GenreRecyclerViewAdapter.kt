package com.squats.moviesapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squats.moviesapp.databinding.MoviesListItemBinding
import com.squats.moviesapp.screens.model.ParentMovieList

class GenreRecyclerViewAdapter(val moviesList: ArrayList<ParentMovieList>) :
    RecyclerView.Adapter<GenreRecyclerViewAdapter.GenreViewHolder>() {
    class GenreViewHolder(val binding: MoviesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    init {
        Log.d("1234", "${moviesList.size}")
    }
    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        Log.d("1234", "abcd1")
        holder.binding.parent = moviesList[position]
        holder.binding.adapter =
            MoviesRecyclerViewAdapter(moviesList[position].moviesListResponseModel?.Search)
        holder.binding.executePendingBindings()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(MoviesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}