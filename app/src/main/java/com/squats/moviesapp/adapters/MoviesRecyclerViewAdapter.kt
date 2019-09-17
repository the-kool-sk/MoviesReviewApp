package com.squats.moviesapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squats.moviesapp.utility.OtherConstants
import com.squats.moviesapp.R
import com.squats.moviesapp.databinding.MovieListItemBinding
import com.squats.moviesapp.screens.model.MoviePosterDetailsResponseModel

class MoviesRecyclerViewAdapter(var list: List<MoviePosterDetailsResponseModel>?) :
    RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.movie = list?.get(position) ?: null
        holder.binding.myadapter = this

    }

    class MovieViewHolder(var binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root)
    fun setListener(v: View, movie: MoviePosterDetailsResponseModel) {
        val bundle = Bundle()
        bundle.putString(OtherConstants.BUNDLE_CONSTANT,movie.imdbID)
        v.findNavController().navigate(R.id.movieDetails,bundle)
    }
}