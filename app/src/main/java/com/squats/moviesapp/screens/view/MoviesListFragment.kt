package com.squats.moviesapp.screens.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.parshwahotel.parshwahotelapp.extentionfunctions.dpToPx
import com.parshwahotel.parshwahotelapp.extentionfunctions.toast

import com.squats.moviesapp.R
import com.squats.moviesapp.adapters.GenreRecyclerViewAdapter
import com.squats.moviesapp.databinding.FragmentMoviesListBinding
import com.squats.moviesapp.screens.viewmodel.MoviesListViewModel
import com.squats.moviesapp.utility.MovieItemDecoration
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment() {

    private lateinit var moviesListViewModel: MoviesListViewModel
    private lateinit var binding: FragmentMoviesListBinding
    private var exit = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        moviesListViewModel = ViewModelProviders.of(this).get(MoviesListViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false)
        // Inflate the layout for this fragment
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (exit) {
                activity?.finishAndRemoveTask()
            } else {
                activity?.application?.toast("Press back again to exit.")
                exit = true
                lifecycleScope.launch {
                    kotlinx.coroutines.delay(3000)
                    exit = false
                }
            }
        }
        callback.isEnabled = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movies_list_recyclerView.layoutManager = LinearLayoutManager(activity)
        movies_list_recyclerView.addItemDecoration(MovieItemDecoration(activity!!.dpToPx(5)))
        initObservers()
        moviesListViewModel.getMoviesByGener()
    }
    private fun initObservers() {
        moviesListViewModel.parentMovieListliveData.observe(this, Observer {
            binding.data = GenreRecyclerViewAdapter(it)
        })
    }

    override fun onResume() {
        super.onResume()
    }

}
