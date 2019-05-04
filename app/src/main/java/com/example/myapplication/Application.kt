package com.example.myapplication

import android.app.Application

public class Application : Application() {

    companion object {
        private lateinit var instance: Application
        fun getInstance(): Application {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}