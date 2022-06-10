package com.example.polutanapp.network

import com.example.polutanapp.response.SavedUser
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SavedUser>
}