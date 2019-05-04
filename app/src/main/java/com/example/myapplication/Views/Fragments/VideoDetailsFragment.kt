package com.example.myapplication.Views.Fragments

import android.app.Activity
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.*
import com.example.myapplication.Models.Movie
import com.example.myapplication.Presenters.CardPresenter
import com.example.myapplication.Presenters.DetailsDescriptionPresenter
import com.example.myapplication.Utils.PicassoBackgroundManager
import com.squareup.picasso.Picasso
import java.lang.Exception
import androidx.leanback.widget.DetailsOverviewRow
import com.example.myapplication.Views.Activities.DetailsActivity


public class VideoDetailsFragment : DetailsSupportFragment() {

    private val DETAIL_THUMB_WIDTH = 274
    private val DETAIL_THUMB_HEIGHT = 274

    private lateinit var mFwDorPresenter: FullWidthDetailsOverviewRowPresenter
    private lateinit var mPicassoBackgroundManager: PicassoBackgroundManager
    private lateinit var mSelectedMovie: Movie
    private lateinit var mDetailsRowBuilderTask: DetailsRowBuilderTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFwDorPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        mPicassoBackgroundManager = PicassoBackgroundManager(activity as Activity)
        mSelectedMovie = activity!!.intent.getSerializableExtra(DetailsActivity.MOVIE_SELECTED) as Movie
        mDetailsRowBuilderTask = DetailsRowBuilderTask().execute(mSelectedMovie) as DetailsRowBuilderTask
    }

    override fun onStop() {
        mDetailsRowBuilderTask.cancel(true)
        super.onStop()
    }

    private inner class DetailsRowBuilderTask : AsyncTask<Movie, Int, DetailsOverviewRow>() {
        override fun doInBackground(vararg params: Movie?): DetailsOverviewRow {
            val row = DetailsOverviewRow(mSelectedMovie)
            try {
                val poster: Bitmap =
                    Picasso.get().load(mSelectedMovie.imageUrl).resize(DETAIL_THUMB_WIDTH, DETAIL_THUMB_HEIGHT)
                        .centerCrop().get()
                row.setImageBitmap(activity, poster)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            return row
        }

        override fun onPostExecute(result: DetailsOverviewRow?) {
            super.onPostExecute(result)
            val sparseArrayObjectAdapter = SparseArrayObjectAdapter()
            for (i in 1..10) {
                sparseArrayObjectAdapter.set(i, Action(i.toLong(), "label1", "label2"))
            }
            result?.actionsAdapter = sparseArrayObjectAdapter

            // second row
            val listRowAdapter: ArrayObjectAdapter = ArrayObjectAdapter(CardPresenter())
            for (i in 0..9) {
                val movie = Movie()
                if (i % 3 == 0) {
                    movie.imageUrl = "http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg"
                } else if (i % 3 == 1) {
                    movie.imageUrl = "http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02630.jpg"
                } else {
                    movie.imageUrl = "http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02529.jpg"
                }
                movie.title = "title$i"
                movie.studio = "studio$i"
                listRowAdapter.add(movie)
            }

            val headerItem = HeaderItem(0, "Related videos")

            val classPresenterSelector = ClassPresenterSelector()
            mFwDorPresenter.initialState = FullWidthDetailsOverviewRowPresenter.STATE_SMALL

            classPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, mFwDorPresenter)
            classPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())

            val adapter: ArrayObjectAdapter = ArrayObjectAdapter(classPresenterSelector)
            adapter.add(result)
            adapter.add(ListRow(headerItem, listRowAdapter))

            setAdapter(adapter)
        }
    }
}