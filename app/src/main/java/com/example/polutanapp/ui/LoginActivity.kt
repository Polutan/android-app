package com.example.polutanapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.polutanapp.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
    }
}