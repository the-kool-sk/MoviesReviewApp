package com.squats.moviesapp.screens.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.parshwahotel.parshwahotelapp.utility.OtherConstants

import com.squats.moviesapp.R
import com.squats.moviesapp.adapters.GenreRecyclerViewAdapter
import com.squats.moviesapp.databinding.FragmentMovieDetailsBinding
import com.squats.moviesapp.screens.viewmodel.MovieDetailsViewModel
import com.squats.moviesapp.screens.viewmodel.MoviesListViewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var binding:FragmentMovieDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        binding=FragmentMovieDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val value=arguments?.getString(OtherConstants.BUNDLE_CONSTANT)
        movieDetailsViewModel.getMovieDetails(value)
        initObservers(movieDetailsViewModel)
    }

    private fun initObservers(movieDetail:MovieDetailsViewModel) {
        movieDetail.movieDetailsLiveData.observe(this, Observer {
            binding.movie=it
        })

    }


}
