package com.example.moviedb.base

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.moviedb.R
import java.lang.StringBuilder

class Util {
    companion object {
        fun getImageUrl(url: String): String {
            return StringBuilder(StringUtils.DEFAULT_IMAGE_URL_PATH).append(url).toString()
        }

        fun showToast(context: Context, msg: String) {
            val toast = Toast(context)
            toast.createToast(context, msg)
        }

        private fun Toast.createToast(context: Context, message: String) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(
                R.layout.custom_toast,
                (context as BaseActivity).findViewById<ViewGroup>(R.id.custom_toast)
            )

            layout.findViewById<TextView>(R.id.text_custom_toast).text = message
            setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }


}