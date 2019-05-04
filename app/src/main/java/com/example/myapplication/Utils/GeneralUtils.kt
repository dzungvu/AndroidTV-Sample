package com.example.myapplication.Utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.WINDOW_SERVICE
import android.graphics.Point
import android.net.ConnectivityManager
import android.view.WindowManager
import android.widget.Toast
import com.example.myapplication.Application


public class GeneralUtils {

    companion object {
        fun isNetworkConnected(): Boolean {
            val cm = Application.getInstance().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun converDpToPixel(context: Context, dp: Int): Int {
            val density = context.resources.displayMetrics.density
            return Math.round(dp * density)
        }

        fun getDisplaySize(context: Context): Point {
            val wm = context.getSystemService(WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)

            return size
        }

        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun showToast(context: Context, resourceId: Int) {
            Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_LONG).show()
        }

        fun formatMillis(millis: Int): String {
            var millis = millis
            var result = ""
            val hr = millis / 3600000
            millis %= 3600000
            val min = millis / 60000
            millis %= 60000
            val sec = millis / 1000
            if (hr > 0) {
                result += "$hr:"
            }
            if (min >= 0) {
                if (min > 9) {
                    result += "$min:"
                } else {
                    result += "0$min:"
                }
            }
            if (sec > 9) {
                result += sec
            } else {
                result += "0$sec"
            }
            return result
        }
    }
}