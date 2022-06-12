package com.example.polutanapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.polutanapp.model.UserModel
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: UserPreference) : ViewModel() {

    val listScore = MutableLiveData<ArrayList<UserModel>>()

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun getScoreAQI(token: String) {
        val client = ApiConfig.getApiService().getPredictData(token)
        client.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val homeResponse = response.body()


                    if (homeResponse != null) {
                        saveUser(
                            UserModel(
                                homeResponse.token,
                                homeResponse.status,
                                homeResponse.message,
                                homeResponse.score
                            )
                        )
//                        val helperArrayList = ArrayList<String>(4)
//                        helperArrayList.add(homeResponse.token)
//                        helperArrayList.add(homeResponse.status.toString())
//                        helperArrayList.add(homeResponse.message)
//                        helperArrayList.add(homeResponse.score.toString())
//
//                        listScore.postValue(helperArrayList)
                    }
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
    }

    fun getListScoreAQI(): LiveData<ArrayList<UserModel>> {
        return listScore
    }


}