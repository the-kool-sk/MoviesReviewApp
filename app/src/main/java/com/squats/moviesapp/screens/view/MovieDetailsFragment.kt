package com.squats.moviesapp.screens.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squats.moviesapp.MainActivity
import com.squats.moviesapp.utility.OtherConstants

import com.squats.moviesapp.databinding.FragmentMovieDetailsBinding
import com.squats.moviesapp.extentionfunctions.gone
import com.squats.moviesapp.extentionfunctions.visible
import com.squats.moviesapp.screens.viewmodel.MovieDetailsViewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var binding: FragmentMovieDetailsBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieDetails()

        initObservers(movieDetailsViewModel)
    }

    private fun getMovieDetails() {
        (activity as MainActivity).isConnected.observe(this, Observer {
            if (it) {
                val value = arguments?.getString(OtherConstants.BUNDLE_CONSTANT)
                movieDetailsViewModel.getMovieDetails(value)
            }
        })
    }

    private fun initObservers(movieDetail: MovieDetailsViewModel) {
        movieDetail.movieDetailsLiveData.observe(this, Observer {
            if(it!=null) {
                binding.movie = it
            }
        })

        movieDetail.isloading.observe(this, Observer {
            if (it) {
                binding.pbMoviesDetails?.visible()
                binding.svMoviesDetails?.gone()
            } else {
                binding.pbMoviesDetails?.gone()
                binding.svMoviesDetails?.visible()
            }
        })

    }


}
