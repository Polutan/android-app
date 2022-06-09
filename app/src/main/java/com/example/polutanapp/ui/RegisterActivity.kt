package com.example.polutanapp.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.polutanapp.R
import com.example.polutanapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        setupView()
        haveAccount()

        registerBinding.btnRegister.setOnClickListener {
            checkValidation()
        }
    }

    private fun setupView() {
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

    private fun checkValidation() {
        val name = registerBinding.textInputEdiTextName.text.toString()
        val email = registerBinding.textInputEdiTextEmail.text.toString()
        val password = registerBinding.textInputEdiTextRegisterPassword.text.toString()

        when {
            name.isEmpty() -> {
                registerBinding.texInputLayoutName.setError("Name can not empty!")
            }
            email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                registerBinding.texInputLayoutEmail.setError("Email is not valid!")
            }
            password.isEmpty() || password.length < 8 -> {
                registerBinding.texInputLayoutRegisterPassword.setError("Password must be > 8")
            }
        }
    }

    private fun haveAccount() {
        registerBinding.tvLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        registerBinding.registerProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}