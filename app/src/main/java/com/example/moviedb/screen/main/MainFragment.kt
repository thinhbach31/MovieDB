package com.example.moviedb.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.base.StringUtils
import com.example.moviedb.screen.home.HomeFragment
import com.example.moviedb.screen.library.LibraryFragment
import com.example.moviedb.screen.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchTab()
        main_bottom_nav.setItemSelected(getTabId(0))
    }

    private fun switchTab() {
        main_bottom_nav.setOnItemSelectedListener {
            when (it) {
                getTabId(StringUtils.NUM_0) -> {
                    HomeFragment().also {
                        activity!!.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_fragment_main, it)
                            .commit()
                    }

                }
                getTabId(StringUtils.NUM_1) -> {
                    SearchFragment().also {
                        activity!!.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_fragment_main, it)
                            .commit()
                    }
                }
                getTabId(StringUtils.NUM_2) -> {
                    LibraryFragment().also {
                        activity!!.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_fragment_main, it)
                            .commit()
                    }
                }
            }
        }
    }

    private fun getTabId(index: Int) = main_bottom_nav.getChildAt(index).id
}