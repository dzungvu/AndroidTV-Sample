package com.example.myapplication.Models

public class Movie() {
    lateinit var title: String
    lateinit var studio: String
    lateinit var imageUrl: String

    constructor(title: String, studio: String, imageUrl: String) : this() {
        this.title = title
        this.studio = studio
        this.imageUrl = imageUrl
    }
}