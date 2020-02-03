package com.example.moviedb.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.base.StringUtils
import com.example.moviedb.listener.MovieItemClickListener
import com.example.moviedb.model.entry.Result
import kotlinx.android.synthetic.main.item_movies.view.*

class MovieAdapter(
    private val movies: ArrayList<Result>,
    private val listener: MovieItemClickListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies, parent, false)
        this.context = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        val imageUrl = StringBuilder(StringUtils.DEFAULT_IMAGE_URL_PATH).append(movie.posterPath)
        Glide.with(context!!).load(imageUrl.toString()).into(holder.itemView.image_item_movies)

        holder.itemView.text_item_movies.text = movie.title

        holder.itemView.setOnClickListener {
            listener.onItemClick(movie)
        }
    }
}
