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
import com.example.moviedb.base.ImageSliderAdapter
import com.example.moviedb.base.StringUtils
import com.example.moviedb.base.StringUtils.Companion.DEFAULT_PAGE
import com.example.moviedb.base.Util
import com.example.moviedb.listener.MovieItemClickListener
import com.example.moviedb.model.entry.ListMoviesEntry
import com.example.moviedb.model.entry.Result
import com.example.moviedb.screen.home.adapter.MovieAdapter
import com.example.moviedb.screen.home.listener.HomeBackpressedListener
import com.example.moviedb.screen.sorted.SortedMovieFragment
import com.example.moviedb.viewmodel.factory.MainVMFactory
import com.example.moviedb.viewmodel.viewmodel.MainViewModel
import com.google.android.material.appbar.AppBarLayout
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), HomeBackpressedListener {

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

        initAppbarLayout()

        movieTypeDetail()

        main_swipe.setOnRefreshListener {
            main_swipe.isRefreshing = true
            refreshList()
        }
    }

    override fun onBackpressListener() {
        startingObserver()
    }

    private fun initAppbarLayout() {
        main_appbar_layout.addOnOffsetChangedListener(object :
            AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout?.totalScrollRange!!
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = false
                    hideOption()
                } else if (!isShow) {
                    isShow = true
                    showOption()
                }
            }
        })
    }

    private fun startingObserver() {
        viewModel.getMovies(DEFAULT_PAGE, StringUtils.NOWPLAYING)
        viewModel.getMovies(DEFAULT_PAGE, StringUtils.UPCOMING)
        viewModel.getMovies(DEFAULT_PAGE, StringUtils.POPULAR)
        viewModel.getMovies(DEFAULT_PAGE, StringUtils.TOPRATED)
    }

    private fun observe() {
        viewModel.getNowPlayingMoviesResponse().observe(viewLifecycleOwner, Observer {
            initSlider(it)
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

    private fun initSlider(it: ListMoviesEntry) {
        val listImage = ArrayList<String>()

        for (movie in it.results) {
            if (listImage.size > 4) {
                break
            }
            listImage.add(movie.posterPath)
        }

        ImageSliderAdapter(context!!, listImage).also {
            home_image_slider.sliderAdapter = it
        }
        home_image_slider.startAutoCycle()
        home_image_slider.setIndicatorAnimation(IndicatorAnimations.WORM)
        home_image_slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
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


    private fun movieTypeDetail() {
        text_home_now_playing.setOnClickListener {
            SortedMovieFragment(StringUtils.NOWPLAYING, this).apply {
                this@HomeFragment.addFragmentAddToStack(
                    R.id.container_main, this, this::class.java.simpleName
                )
            }
        }

        text_home_popular.setOnClickListener {
            SortedMovieFragment(StringUtils.POPULAR, this).apply {
                this@HomeFragment.addFragmentAddToStack(
                    R.id.container_main, this, this::class.java.simpleName
                )
            }
        }

        text_home_top_rate.setOnClickListener {
            SortedMovieFragment(StringUtils.TOPRATED, this).apply {
                this@HomeFragment.addFragmentAddToStack(
                    R.id.container_main, this, this::class.java.simpleName
                )
            }
        }

        text_home_upcoming.setOnClickListener {
            SortedMovieFragment(StringUtils.UPCOMING, this).apply {
                this@HomeFragment.addFragmentAddToStack(
                    R.id.container_main, this, this::class.java.simpleName
                )
            }
        }
    }

    private fun refreshList() {
        main_swipe.isRefreshing = false
        startingObserver()
        Util.showToast(context!!, getString(R.string.title_updated))
    }

    private fun showOption() {
        home_image_slider.visibility = View.VISIBLE
    }

    private fun hideOption() {
        home_image_slider.visibility = View.INVISIBLE
    }
}
