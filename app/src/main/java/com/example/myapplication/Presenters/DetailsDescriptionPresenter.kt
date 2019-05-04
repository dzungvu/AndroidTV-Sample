package com. example.myapplication.Presenters

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.example.myapplication.Models.Movie

public class DetailsDescriptionPresenter: AbstractDetailsDescriptionPresenter() {
    override fun onBindDescription(vh: ViewHolder?, item: Any?) {
        val movie: Movie? = item as Movie
        vh?.title!!.text = movie?.title!!
        vh.subtitle.text = movie.studio
        vh.body.text = movie.description
    }

}