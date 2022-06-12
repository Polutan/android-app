package com.example.polutanapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.polutanapp.model.UserModel
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.network.ApiConfig
import com.example.polutanapp.response.SavedUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: UserPreference) : ViewModel() {
    val listScore = MutableLiveData<Int>()

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getScoreAQI(token: String) {
        val client = ApiConfig.getApiService().getPredictData(token)
        client.enqueue(object : Callback<SavedUser>{
            override fun onResponse(call: Call<SavedUser>, response: Response<SavedUser>) {
                if (response.isSuccessful) {
                    listScore.postValue(response.body()?.score)
                }
            }

            override fun onFailure(call: Call<SavedUser>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
    }

    fun getListScoreAQI(): LiveData<Int>{
        return listScore
    }



}