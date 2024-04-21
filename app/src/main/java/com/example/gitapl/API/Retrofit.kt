package com.example.gitapl.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE = "https://api.github.com"

    val retro = Retrofit.Builder()
        .baseUrl(BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance = retro.create(API::class.java)
}