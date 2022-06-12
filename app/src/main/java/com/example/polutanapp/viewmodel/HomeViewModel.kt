package com.example.polutanapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.polutanapp.model.UserModel
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.network.ApiConfig
import com.example.polutanapp.response.SavedUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: UserPreference) : ViewModel() {

    val listScore = MutableLiveData<Int>()

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

//    fun saveAQIScore(savedUser: SavedUser){
//        viewModelScope.launch {
//            pref.savedScoreAQI(savedUser)
//        }
//
//    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun getScoreAQI(token: String) {
        val client = ApiConfig.getApiService().getPredictData(token)
        client.enqueue(object : Callback<UserModel>{
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val homeResponse = response.body()

                    listScore.postValue(response.body()?.score)
                    if (homeResponse != null) {
                        saveUser(UserModel(homeResponse.token,homeResponse.status,homeResponse.message,homeResponse.score))
                    }
//                    if (homeResponse != null) {
//                        saveAQIScore(SavedUser(homeResponse.password,homeResponse.nama,homeResponse.V,homeResponse.id,homeResponse.email,homeResponse.score))
//                    }
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
    }

    fun getListScoreAQI(): LiveData<Int>{
        return listScore
    }



}