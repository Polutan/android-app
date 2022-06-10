package com.example.polutanapp.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("savedUser")
	val savedUser: SavedUser,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class SavedUser(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("email")
	val email: String
)
