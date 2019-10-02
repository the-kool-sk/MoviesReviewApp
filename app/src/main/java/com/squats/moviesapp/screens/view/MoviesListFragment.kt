package com.squats.moviesapp.screens.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.squats.moviesapp.MainActivity
import com.squats.moviesapp.R
import com.squats.moviesapp.adapters.GenreRecyclerViewAdapter
import com.squats.moviesapp.adapters.MoviesRecyclerViewAdapter
import com.squats.moviesapp.databinding.FragmentMoviesListBinding
import com.squats.moviesapp.extentionfunctions.dpToPx
import com.squats.moviesapp.extentionfunctions.gone
import com.squats.moviesapp.extentionfunctions.toast
import com.squats.moviesapp.extentionfunctions.visible
import com.squats.moviesapp.screens.viewmodel.MoviesListViewModel
import com.squats.moviesapp.utility.MovieItemDecoration
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.movies_list_item.*
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment() {

    private lateinit var moviesListViewModel: MoviesListViewModel
    private lateinit var binding: FragmentMoviesListBinding
    //    private lateinit var connectionLiveData: ConnectionLiveData
    private var exit = false
    private var isCalledFromOnCreated = false
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        moviesListViewModel = ViewModelProvider(this).get(MoviesListViewModel::class.java)
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
        observeNetworkConnection()
        return binding.root
    }

    private fun downloadList() {
        if (movies_list_recyclerView.isEmpty() || moviesList_item_recyclerView.isEmpty()) {
            moviesListViewModel.getMoviesByGener()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movies_list_recyclerView.layoutManager = LinearLayoutManager(activity)
        movies_list_recyclerView.addItemDecoration(MovieItemDecoration(activity!!.dpToPx(5)))
        initObservers()
        downloadList()
        isCalledFromOnCreated = true
    }

    private fun observeNetworkConnection() {
        (activity as MainActivity).isConnected.observe(this, Observer {
            if (it && !isCalledFromOnCreated) {
                downloadList()
            } else {
                isCalledFromOnCreated = false
            }
        })
    }

    private fun initObservers() {
        moviesListViewModel.parentMovieListliveData.observe(this, Observer {
            if (movies_list_recyclerView.isEmpty() || moviesList_item_recyclerView.isEmpty()) {
                binding.data = MoviesRecyclerViewAdapter(it)
            }
        })

        moviesListViewModel.isloading.observe(this, Observer {
            if (it) {
                binding.pbMoviesList?.visible()
                binding.srlMoviesList?.gone()
            } else {
                binding.pbMoviesList?.gone()
                binding.srlMoviesList?.visible()
            }
        })
    }

}
