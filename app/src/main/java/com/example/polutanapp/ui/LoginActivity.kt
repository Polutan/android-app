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
import com.example.polutanapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        setupView()
        setUpAction()


        loginBinding.btnLogin.setOnClickListener {
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
        val email = loginBinding.textInputEdiTextEmail.text.toString()
        val password = loginBinding.textInputEdiTextPassword.text.toString()

        when {
            email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                loginBinding.texInputLayoutEmail.setError("Email is not valid!")
            }
            password.isEmpty() || password.length < 8 -> {
                loginBinding.texInputLayoutPassword.setError("Password must be > 8")
            }
        }
    }

    private fun setUpAction() {
        loginBinding.tvDontHaveAccount.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        })

        loginBinding.tvLupaPassword.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, ResetPasswordActivity::class.java)
            startActivity(intent)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        loginBinding.loginProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}