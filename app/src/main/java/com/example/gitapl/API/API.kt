package com.example.gitapl.API

import com.example.gitapl.detailpage.Detail
import com.example.gitapl.mod.Response
import com.example.gitapl.mod.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    @Headers("Authorization: token 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")

    fun getUsers (@Query("q") query: String
    ): Call<Response>

    @GET("users/{id}")
    @Headers("Authorization: token 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")

    fun getDetails (@Path("id") username: String
    ): Call<Detail>

    @GET("users/{id}/followers")
    @Headers("Authorization: token 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")

    fun getFollower (@Path("id") followers: String
    ): Call<ArrayList<User>>

    @GET("users/{id}/following")
    @Headers("Authorization: token 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")

    fun getFollowing (@Path("id") following: String
    ): Call<ArrayList<User>>
}