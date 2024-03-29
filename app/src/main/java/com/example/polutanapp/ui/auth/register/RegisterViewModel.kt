package com.example.polutanapp.ui.auth.register

import android.util.Log
import androidx.lifecycle.*
import com.example.polutanapp.data.Resource
import com.example.polutanapp.model.UserModel
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.network.ApiConfig
import com.example.polutanapp.response.SavedUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun checker(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun register(name: String, email: String, password: String): LiveData<Resource<Boolean>> {
        val register = MutableLiveData<Resource<Boolean>>()
        register.value = Resource.Loading
        _isLoading.value = true
        ApiConfig.getApiService().register(
            name, email, password
        ).enqueue(object : Callback<SavedUser> {
            override fun onResponse(
                call: Call<SavedUser>,
                response: Response<SavedUser>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        checker(UserModel("", 200, "",0))
                        register.value = Resource.Success(true)
                    } else {
                        Log.d("onFailure", response.message())
                    }
                } else {
                    register.value = Resource.Error("Gagal!")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<SavedUser>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
        return register
    }
}