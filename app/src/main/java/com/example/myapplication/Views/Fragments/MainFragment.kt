package com.example.myapplication.Views.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.example.myapplication.Models.Movie
import com.example.myapplication.Presenters.CardPresenter
import com.example.myapplication.R
import com.example.myapplication.Utils.GeneralUtils
import com.example.myapplication.Utils.PicassoBackgroundManager
import com.example.myapplication.Utils.SimpleBackgroundManager
import com.example.myapplication.Views.Activities.DetailsActivity

class MainFragment : BrowseSupportFragment() {

    private lateinit var simpleBackgroundManager: SimpleBackgroundManager
    private lateinit var picassoBackgroundManager: PicassoBackgroundManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (GeneralUtils.isNetworkConnected()) {
            Log.d("Connection status:", "Connected")
            Toast.makeText(activity, "Internet connected", Toast.LENGTH_LONG).show()
        } else {
            Log.d("Connection status", "Not connected")
        }

        simpleBackgroundManager = SimpleBackgroundManager(activity as Activity)
        picassoBackgroundManager = PicassoBackgroundManager(activity as Activity)

        setUpUI()
        loadRows()
        setupListener()
    }

    private fun setUpUI() {
        title = getString(R.string.header_title)
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = ResourcesCompat.getColor(resources, R.color.fastlane_background, null)
        searchAffordanceColor = ResourcesCompat.getColor(
            resources,
            R.color.search_opaque, null
        )
    }

    private fun loadRows() {
        val mRowAdapter = ArrayObjectAdapter(ListRowPresenter())
        val gridItemPresenterHeader = HeaderItem(0, "Productions")
        val gridItemPresenter = GridItemPresenter()
        val gridRowAdapter = ArrayObjectAdapter(gridItemPresenter)
        gridRowAdapter.add("Netflix")
        gridRowAdapter.add("Amazon")
        gridRowAdapter.add("Disney Plus")
        mRowAdapter.add(ListRow(gridItemPresenterHeader, gridRowAdapter))

        val cardItemPresenter = CardPresenter()
        val cardPresenterHeader = HeaderItem(1, "Movies")
        val cardRowAdapter = ArrayObjectAdapter(cardItemPresenter)

        val attackOnTitanMovie = Movie(
            "Attack on Titan",
            "Shingeki no Kyojin",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOf34D9iyL3_WvhA7194iK0Z_DYcgAlXssdi0RLs1PQA0iDxEK2Q"
        )
        attackOnTitanMovie.description = "Description"
        val hunterXHunterMovie = Movie(
            "Hunter X Hunter",
            "Yoshihiro Togashi",
            "https://www.dhresource.com/albu_1081228904_00-1.0x0/hunter-x-hunter-silk-wall-poster-36x24-18x12.jpg"
        )
        hunterXHunterMovie.description = "Description"
        val tokyoGhoulMovie = Movie(
            "Tokyo Ghoul",
            "Sui Ishida",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGRsUzwCUI55D0CVxZ3nLcqwovj2vUNqHkWpJOSBP85Rpg_Knn9Q"
        )
        tokyoGhoulMovie.description = "Description"
        val dragonBallZMovie = Movie(
            "Dragon Ball Z",
            "Akira Toriyama",
            "https://cdn.shopify.com/s/files/1/0747/3829/products/mGR0108_1024x1024.jpeg?v=1485013733"
        )
        dragonBallZMovie.description = "Description"

        cardRowAdapter.add(attackOnTitanMovie)
        cardRowAdapter.add(hunterXHunterMovie)
        cardRowAdapter.add(tokyoGhoulMovie)
        cardRowAdapter.add(dragonBallZMovie)

        mRowAdapter.add(ListRow(cardPresenterHeader, cardRowAdapter))

        adapter = mRowAdapter
    }

    private fun setupListener() {
        onItemViewSelectedListener = ItemViewSelectedListener()
        onItemViewClickedListener = ItemViewClickedListener()
    }

    private class GridItemPresenter : Presenter() {
        val TEXTVIEW_TITLE_HEIGHT: Int = 200
        val TEXTVIEW_TITLE_WIDTH: Int = 300

        @SuppressLint("ResourceAsColor")
        override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
            val tvTitle = TextView(parent!!.context)
            tvTitle.layoutParams = ViewGroup.LayoutParams(TEXTVIEW_TITLE_WIDTH, TEXTVIEW_TITLE_HEIGHT)
            tvTitle.isFocusable = true
            tvTitle.isFocusableInTouchMode = true
            tvTitle.setBackgroundColor(R.color.default_background)
            tvTitle.setTextColor(Color.WHITE)
            tvTitle.gravity = Gravity.CENTER

            return ViewHolder(tvTitle)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
            (viewHolder!!.view as TextView).setText(item as String)
        }

        override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is String) {
//                simpleBackgroundManager.clearBackground()
                picassoBackgroundManager.updateBackgroundWithDelay("https://i.pinimg.com/originals/cb/98/62/cb9862e88cbf382f0a5e7f812cc356a8.jpg")
            } else if (item is Movie) {
//                simpleBackgroundManager.updateBackground(Application.getInstance().getDrawable(R.drawable.mainbg)!!)
                picassoBackgroundManager.updateBackgroundWithDelay("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSE-ANkz86ibTb56YxxDo-poucIgrG03ydyScpmmPAciAFGxM0i")
            }
        }
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is Movie) {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE_SELECTED, item)

                activity?.startActivity(intent)
            }
        }
    }
}
