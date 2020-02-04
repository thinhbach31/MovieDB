package com.example.moviedb.base

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    fun onBackPressed() {
        activity?.onBackPressed()
    }

    fun addFragmentAddToStack(container: Int, fragment: BaseFragment, fragmentName: String) {
        fragment.also {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(container, it)?.addToBackStack(fragmentName)?.commit()
        }
    }

    fun addFragmentWithoutAddToStack(container: Int, fragment: BaseFragment) {
        fragment.also {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(container, it)?.commit()
        }
    }

    fun hideKeyboard() {
        val imm =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity?.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}