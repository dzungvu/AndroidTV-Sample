package com.example.myapplication.Utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.leanback.app.BackgroundManager
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import java.net.URI
import java.util.*

class PicassoBackgroundManager(activity: Activity) {

    private val DEFAULT_IMAGE_BACKGROUND_ID = R.drawable.textbg

    private var mDefaultBackground: Drawable
    private var activity = activity
    private var mBackgroundManager: BackgroundManager
    private var mBackgroundTarget: PicassoBackgroundManagerTarget
    private val mHandler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private var mBackgroundURI: URI? = null
    private var mBackgroundTimer: Timer? = null
    private val BACKGROUND_UPDATE_DELAY: Long by lazy { 500L }
    private var mDisplayMetric: DisplayMetrics = DisplayMetrics()

    init {
        mDefaultBackground = activity.getDrawable(DEFAULT_IMAGE_BACKGROUND_ID)!!
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundTarget = PicassoBackgroundManagerTarget(mBackgroundManager)
        mBackgroundManager.attach(activity.window)
        activity.windowManager.defaultDisplay.getMetrics(mDisplayMetric)
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer!!.schedule(UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY)
    }

    fun updateBackground(uri: URI) {
        try {
            Picasso.get()
                .load(uri.toString())
                .resize(mDisplayMetric.widthPixels, mDisplayMetric.heightPixels)
                .centerCrop()
                .error(mDefaultBackground)
                .into(mBackgroundTarget)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    private inner class UpdateBackgroundTask : TimerTask() {
        override fun run() {
            mHandler.post { mBackgroundURI?.let { updateBackground(mBackgroundURI!!) } }
        }

    }

    public fun updateBackgroundWithDelay(urlString: String) {
        try {
            val uri = URI(urlString)
            updateBackgroundWithDelay(uri)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    public fun updateBackgroundWithDelay(uri: URI) {
        mBackgroundURI = uri
        startBackgroundTimer()
    }
}