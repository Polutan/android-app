package com.example.polutanapp.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.polutanapp.ViewModelFactory
import com.example.polutanapp.data.Resource
import com.example.polutanapp.databinding.ActivityLoginBinding
import com.example.polutanapp.model.UserModel
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.viewmodel.LoginViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var user: UserModel
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        setupView()
        setupViewModel()
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

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this, { user ->
            this.user = user
        })

        loginViewModel.isLoading.observe(this, {
            showLoading(it)
        })
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
            else -> {
                loginViewModel.login(email, password).observe(this) {
                    when (it) {
                        is Resource.Success -> {
                            AlertDialog.Builder(this).apply {
                                setTitle("Berhasil!")
                                setMessage("Anda Berhasil Login, silahkan klik lanjut")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(context, HomeActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this,
                                "Mohon Maaf, Password yang Anda Masukkan salah!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
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