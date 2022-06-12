package com.example.polutanapp.network

import com.example.polutanapp.model.UserModel
import com.example.polutanapp.response.SavedUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SavedUser>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserModel>

    @GET("predict")
    fun getPredictData(
        @Header("auth-token") token:String,
    ): Call<UserModel>

}