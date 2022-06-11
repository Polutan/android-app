package com.example.polutanapp.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.polutanapp.R
import com.example.polutanapp.ViewModelFactory
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.viewmodel.SplashViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUpView()
        setUpViewModel()
        setUpAction()

    }

    private fun setUpView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setUpViewModel() {
        splashViewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SplashViewModel::class.java]
    }

    private fun setUpAction() {
        handler = Handler(mainLooper)
        handler.postDelayed({
            splashViewModel.getUser().observe(this) {
                if (it.token.isNotEmpty()) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 2000)
    }
}