package com.example.moviedb.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.viewmodel.factory.MainVMFactory
import com.example.moviedb.viewmodel.viewmodel.MainViewModel

class HomeFragment : BaseFragment() {

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

        viewModel.getListMovies(1)
    }

    private fun observe(){
        viewModel.getListMoviesResponse().observe(viewLifecycleOwner, Observer {
            it
        })
    }
}