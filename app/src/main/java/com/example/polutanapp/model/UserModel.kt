package com.example.polutanapp.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String
)
