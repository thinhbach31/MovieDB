package com.example.moviedb.screen.sorted

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.base.StringUtils
import com.example.moviedb.base.StringUtils.Companion.DEFAULT_PAGE
import com.example.moviedb.base.Util
import com.example.moviedb.listener.MovieItemClickListener
import com.example.moviedb.model.entry.ListMoviesEntry
import com.example.moviedb.model.entry.Result
import com.example.moviedb.screen.home.adapter.MovieAdapter
import com.example.moviedb.screen.home.listener.HomeBackpressedListener
import com.example.moviedb.viewmodel.factory.MainVMFactory
import com.example.moviedb.viewmodel.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.appbar_with_back_button.*
import kotlinx.android.synthetic.main.fragment_sorted_movie.*

const val NOW_PLAYING: String = "Now Playing"
const val UPCOMING: String = "Upcoming"
const val POPULAR: String = "Popular"
const val TOP_RATED: String = "Top Rated"

class SortedMovieFragment(
    private val type: String,
    private val listener: HomeBackpressedListener
) :
    BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private var adapterMovie: MovieAdapter? = null
    private var listMovie = ArrayList<Result>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MainVMFactory().also {
            viewModel = ViewModelProviders.of(activity!!, it).get(MainViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragment_sorted_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        button_appbar_with_button_back.setOnClickListener {
            onBackPressed()
            //viewModel.clearData()
            listener.onBackpressListener()
        }
        initRecycler()
        viewModel.getMovies(DEFAULT_PAGE, type)
    }

    private fun observe() {
        when (type) {
            StringUtils.POPULAR -> {
                text_appbar_name.text = POPULAR
                viewModel.getPopularMoviesResponse().observe(viewLifecycleOwner, Observer {
                    adapterMovie?.updateData(it.results)
                    initPaging(it)
                })
            }

            StringUtils.UPCOMING -> {
                text_appbar_name.text = UPCOMING
                viewModel.getUpcomingMoviesResponse().observe(viewLifecycleOwner, Observer {
                    adapterMovie?.updateData(it.results)
                    initPaging(it)
                })
            }

            StringUtils.NOWPLAYING -> {
                text_appbar_name.text = NOW_PLAYING
                viewModel.getNowPlayingMoviesResponse().observe(viewLifecycleOwner, Observer {
                    adapterMovie?.updateData(it.results)
                    initPaging(it)
                })
            }

            StringUtils.TOPRATED -> {
                text_appbar_name.text = TOP_RATED
                viewModel.getTopRateMoviesResponse().observe(viewLifecycleOwner, Observer {
                    adapterMovie?.updateData(it.results)
                    initPaging(it)
                })
            }
        }
    }

    private fun initRecycler() {
        adapterMovie = MovieAdapter(listMovie, object : MovieItemClickListener {
            override fun onItemClick(result: Result) {

            }
        })
        recycler_sorted_movies.adapter = adapterMovie
        recycler_sorted_movies.layoutManager = GridLayoutManager(context, 2)
    }

    private fun initPaging(it: ListMoviesEntry) {
        text_page_total.text = StringBuilder("of ").append(it.totalPages)
        text_sorted_paging.setText(it.page.toString())
        text_sorted_paging.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboard()
                    if (isCorrectPage(it)) {
                        Util.showToast(context!!, "Please text the right number!")
                        text_sorted_paging.setText(it.page.toString())
                    } else {
                        viewModel.getMovies(text_sorted_paging.text.toString().toInt(), type)
                    }
                    return true
                }
                return false
            }

        })
    }

    private fun isCorrectPage(it: ListMoviesEntry): Boolean =
        text_sorted_paging.text.toString() == "" ||
                text_sorted_paging.text.toString().toInt() > it.totalPages ||
                text_sorted_paging.text.toString() == "0"
}