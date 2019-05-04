package com.example.myapplication.Models

import java.io.Serializable

public class Movie(): Serializable {
    lateinit var title: String
    lateinit var studio: String
    lateinit var imageUrl: String
    lateinit var description: String

    constructor(title: String, studio: String, imageUrl: String) : this() {
        this.title = title
        this.studio = studio
        this.imageUrl = imageUrl
    }
}