package com.squats.moviesapp.screens.view


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squats.moviesapp.MainActivity
import com.squats.moviesapp.R
import com.squats.moviesapp.adapters.MoviesRecyclerViewAdapter
import com.squats.moviesapp.databinding.FragmentMoviesListBinding
import com.squats.moviesapp.extentionfunctions.dpToPx
import com.squats.moviesapp.extentionfunctions.gone
import com.squats.moviesapp.extentionfunctions.toast
import com.squats.moviesapp.extentionfunctions.visible
import com.squats.moviesapp.screens.model.MoviePosterDetailsResponseModel
import com.squats.moviesapp.screens.viewmodel.MoviesListViewModel
import com.squats.moviesapp.utility.MovieItemDecoration
import kotlinx.coroutines.launch


class MoviesListFragment : Fragment() {

    private lateinit var moviesListViewModel: MoviesListViewModel
    private lateinit var binding: FragmentMoviesListBinding
    //    private lateinit var connectionLiveData: ConnectionLiveData
    private var exit = false
    private var isCalledFromOnCreated = false
    var isLoading = false
    private var page = 1

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
        binding.data = MoviesRecyclerViewAdapter(moviesListViewModel.parentMovieList)
        observeNetworkConnection()
        return binding.root
    }

    private fun downloadList(page: Int) {
//        if (binding.moviesListRecyclerView.isEmpty()) {
        moviesListViewModel.getMoviesByGener(page)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesListRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.moviesListRecyclerView.addItemDecoration(MovieItemDecoration(activity!!.dpToPx(5)))
        initObservers()
        downloadList(page)
        isCalledFromOnCreated = true
    }

    private fun observeNetworkConnection() {
        (activity as MainActivity).isConnected.observe(this, Observer {
            if (it && !isCalledFromOnCreated) {
                downloadList(page)
            } else {
                isCalledFromOnCreated = false
            }
        })
    }

    private fun initObservers() {
        moviesListViewModel.parentMovieListliveData.observe(this, Observer {
//            if (binding.moviesListRecyclerView.isEmpty()) {
            binding.data?.notifyDataSetChanged()
            binding.srlMoviesList.isRefreshing = false
            isLoading = false

//            }
        })

        moviesListViewModel.isloading.observe(this, Observer {
            if (page == 1) {
                if (it) {
                    binding.pbMoviesList?.visible()
                    binding.srlMoviesList?.gone()
                } else {
                    binding.pbMoviesList?.gone()
                    binding.srlMoviesList?.visible()
                }
            }
        })

        binding.srlMoviesList.setOnRefreshListener {
            page = 1
            moviesListViewModel.clearList()
            downloadList(page)
        }

        binding.moviesListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == moviesListViewModel.parentMovieList.size - 1) {
                        //bottom of list!
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        moviesListViewModel.parentMovieList.add(
                MoviePosterDetailsResponseModel(
                        null,
                        null,
                        null,
                        null,
                        null))
        binding.data?.notifyItemInserted(moviesListViewModel.parentMovieList.size - 1)

        val handler = Handler()
        handler.postDelayed(Runnable {
            moviesListViewModel.parentMovieList.removeAt(moviesListViewModel.parentMovieList.size - 1)
            val scrollPosition = moviesListViewModel.parentMovieList.size
            binding.data?.notifyItemRemoved(scrollPosition)
            page++
            downloadList(page)
        }, 2000)


    }

}
