package com.example.myapplication.Utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.leanback.app.BackgroundManager
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class PicassoBackgroundManagerTarget(backgroundManager: BackgroundManager): Target {

    private var mBackgroundManager = backgroundManager

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        // No implement
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        e?.printStackTrace()
        mBackgroundManager.drawable = errorDrawable
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        mBackgroundManager.setBitmap(bitmap)
    }

}