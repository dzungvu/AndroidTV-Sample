package com.example.myapplication.Utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.leanback.app.BackgroundManager
import com.example.myapplication.R

class SimpleBackgroundManager(activity: Activity) {
    private var mActivity: Activity = activity

    private val DEFAULT_IMAGE_RESOURCE_ID = R.drawable.textbg
    private var mDefaultBackground: Drawable
    private var mBackgroundManager: BackgroundManager

    init {
        mDefaultBackground = activity.getDrawable(DEFAULT_IMAGE_RESOURCE_ID)!!
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(activity.window)
        activity.windowManager.defaultDisplay.getMetrics(DisplayMetrics())
    }

    public fun updateBackground(drawable: Drawable) {
        mBackgroundManager.drawable = drawable
    }

    public fun clearBackground() {
        mBackgroundManager.drawable = mDefaultBackground
    }
}