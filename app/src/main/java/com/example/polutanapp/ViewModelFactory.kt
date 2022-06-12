package com.example.polutanapp

import androidx.lifecycle.ViewModel
import com.example.polutanapp.model.UserPreference
import androidx.lifecycle.ViewModelProvider
import com.example.polutanapp.viewmodel.*

//import com.example.mystoryapp.models.UserPreference
//import com.example.mystoryapp.repository.StoryRepository
//import com.example.mystoryapp.viewmodel.*


class ViewModelFactory(
    private val pref: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(pref) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
//            modelClass.isAssignableFrom(DetailStoryViewModel::class.java) -> {
//                DetailStoryViewModel(pref) as T
//            }
//            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
//                MapsViewModel(pref) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}