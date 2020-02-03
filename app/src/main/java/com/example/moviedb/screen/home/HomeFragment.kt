package com.example.moviedb.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.listener.MovieItemClickListener
import com.example.moviedb.model.entry.ListMoviesEntry
import com.example.moviedb.model.entry.Result
import com.example.moviedb.screen.home.adapter.MovieAdapter
import com.example.moviedb.viewmodel.factory.MainVMFactory
import com.example.moviedb.viewmodel.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private val DEFAULT_PAGE = 1

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MainVMFactory().apply {
            viewModel = ViewModelProviders.of(activity!!, this)
                .get(MainViewModel::class.java)
        }

        observe()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startingObserver()

        main_swipe.setOnRefreshListener {
            main_swipe.isRefreshing = true
            refreshList()
        }
    }

    private fun startingObserver() {
        viewModel.getPopularMovies(DEFAULT_PAGE)
        viewModel.getNowPlayingMovies(DEFAULT_PAGE)
        viewModel.getUpcomingMovies(DEFAULT_PAGE)
        viewModel.getTopRateMovies(DEFAULT_PAGE)
    }

    private fun observe() {
        viewModel.getNowPlayingMoviesResponse().observe(viewLifecycleOwner, Observer {
            initRecycler(recycler_home_now_playing, it)

        })

        viewModel.getPopularMoviesResponse().observe(viewLifecycleOwner, Observer {
            initRecycler(recycler_home_popular, it)
        })

        viewModel.getUpcomingMoviesResponse().observe(viewLifecycleOwner, Observer {
            initRecycler(recycler_home_upcoming, it)
        })

        viewModel.getTopRateMoviesResponse().observe(viewLifecycleOwner, Observer {
            initRecycler(recycler_home_top_rate, it)
        })

    }

    private fun initRecycler(recycler: RecyclerView, it: ListMoviesEntry) {
        val adapter = MovieAdapter(it.results, object : MovieItemClickListener {

            override fun onItemClick(result: Result) {
                Toast.makeText(context, result.title, Toast.LENGTH_SHORT).show()
            }
        })

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(
            context, RecyclerView.HORIZONTAL,
            false
        )
        recycler.setHasFixedSize(true)
    }

    private fun refreshList() {
        main_swipe.isRefreshing = false
        startingObserver()
        Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
    }
}