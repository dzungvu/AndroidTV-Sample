package com.example.myapplication.Presenters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.example.myapplication.Models.Movie
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

public class CardPresenter : Presenter() {
    lateinit var context: Context
    val MAIN_IMAGE_WIDTH: Int = 313
    val MAIN_IMAGE_HEIGHT: Int = 176

    class MyViewHolder(view: View?) : Presenter.ViewHolder(view) {
        val mCardView: ImageCardView = view as ImageCardView

        val mDefaultCardImage: Drawable = (view!!.context.getDrawable(R.drawable.card_image))!!

        private var movie: Movie
            get() = movie
            set(value) {
                this.movie = value
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        context = parent!!.context
        val cardView = ImageCardView(context)
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.default_background))
        return MyViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val movie: Movie = item as Movie
        var mViewHolder: MyViewHolder = viewHolder as MyViewHolder
        mViewHolder.mCardView.titleText = movie.title
        mViewHolder.mCardView.contentText = movie.studio
        mViewHolder.mCardView.setMainImageDimensions(MAIN_IMAGE_WIDTH, MAIN_IMAGE_HEIGHT)
//        mViewHolder.mCardView.mainImage = mViewHolder.mDefaultCardImage
        Picasso.get().load(movie.imageUrl).resize(MAIN_IMAGE_WIDTH, MAIN_IMAGE_HEIGHT)
            .error(mViewHolder.mDefaultCardImage).into(mViewHolder.mCardView.mainImageView)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}