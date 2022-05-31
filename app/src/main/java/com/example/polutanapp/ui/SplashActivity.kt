package com.example.polutanapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.polutanapp.R

class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val intent = Intent(this, LoginActivity::class.java)

        handler = Handler(mainLooper)
        handler.postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }
}