package com.example.myapplication.Utils

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.example.myapplication.Application


public class GeneralUtils {

    companion object {
        fun isNetworkConnected(): Boolean {
            val cm = Application.getInstance().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}