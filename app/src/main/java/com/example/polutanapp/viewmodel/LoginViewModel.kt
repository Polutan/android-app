package com.example.polutanapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.polutanapp.data.Resource
import com.example.polutanapp.model.UserModel
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun login(email: String, password: String): LiveData<Resource<Boolean>> {
        val login = MutableLiveData<Resource<Boolean>>()
        login.value = Resource.Loading
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(
           email, password
        )
        client.enqueue(object : Callback<UserModel> {
            override fun onResponse(
                call: Call<UserModel>,
                response: Response<UserModel>
            ) {
                val loginResponse = response.body()
                if (response.isSuccessful) {
                    if (loginResponse?.status != 400) {
                        viewModelScope.launch {
                            pref.login()
                        }
                        saveUser(UserModel(loginResponse!!.token,loginResponse.status, loginResponse.message))
                        login.value = Resource.Success(true)
                    } else {
                        Log.d("onFailure", response.message())
                    }
                } else {
                    login.value = Resource.Error("Gagal!")
                    saveUser(UserModel("",400,""))
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
        return login
    }

}