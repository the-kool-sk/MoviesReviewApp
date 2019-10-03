package com.squats.moviesapp.adapters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squats.moviesapp.databinding.ItemLoadingBinding
import com.squats.moviesapp.databinding.MovieListItemBinding
import com.squats.moviesapp.screens.model.MoviePosterDetailsResponseModel
import com.squats.moviesapp.utility.OtherConstants


class MoviesRecyclerViewAdapter(var list: List<MoviePosterDetailsResponseModel>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_ITEM) {
            return MovieViewHolder(
                    MovieListItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )
        }else{
            return LoadingViewHolder(
                    ItemLoadingBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (list?.get(position)?.Title == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MovieViewHolder) {
            holder.binding.movie = list?.get(position) ?: null
            holder.binding.myadapter = this
        }else if(holder is LoadingViewHolder) {

        }

    }

    class MovieViewHolder(var binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root)

    class LoadingViewHolder(var binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}