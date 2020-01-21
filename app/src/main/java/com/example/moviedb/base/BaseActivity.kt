package com.example.moviedb.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    fun moveActivityWithoutAddToBackstack(activity: BaseActivity) {
        Intent().also {
            it.setClass(this, activity::class.java)
            startActivity(it)
            finish()
        }
    }

    fun addFragmentAddToStack(container: Int, fragment: BaseFragment, fragmentName: String) {
        fragment.also {
            supportFragmentManager.beginTransaction()
                .add(container, it).addToBackStack(fragmentName).commit()
        }
    }

    fun addFragmentWithoutAddToStack(container: Int, fragment: BaseFragment) {
        fragment.also {
            supportFragmentManager.beginTransaction()
                .add(container, it).commit()
        }
    }
}