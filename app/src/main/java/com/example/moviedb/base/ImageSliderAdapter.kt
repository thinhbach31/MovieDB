package com.example.moviedb.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.smarteist.autoimageslider.SliderViewAdapter

class ImageSliderAdapter(private var context: Context, private var listImage: ArrayList<String>) :
    SliderViewAdapter<ImageSliderAdapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_slider_image, parent, false)
        return SliderViewHolder(view)
    }

    override fun getCount(): Int {
        return listImage.size
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        val image = listImage[position]
        val imageUrl = StringBuilder(StringUtils.DEFAULT_IMAGE_URL_PATH).append(image)
        Glide.with(context).load(imageUrl.toString()).into(viewHolder.imageViewBackground)
    }

    class SliderViewHolder(itemView: View) :
        ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)

    }
}