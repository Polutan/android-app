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
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.polutanapp.ViewModelFactory
import com.example.polutanapp.databinding.ActivityRegisterBinding
import com.example.polutanapp.model.UserModel
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.viewmodel.RegisterViewModel
import androidx.datastore.preferences.core.Preferences
import com.example.polutanapp.data.Resource

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var user: UserModel
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        setupView()
        setupViewModel()
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

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]

        registerViewModel.getUser().observe(this, { user ->
            this.user = user
        })

        registerViewModel.isLoading.observe(this, {
            showLoading(it)
        })
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
            else -> {
                registerViewModel.register(name, email, password).observe(this) {
                    when (it) {
                        is Resource.Success -> {
                            AlertDialog.Builder(this).apply {
                                setTitle("Berhasil!")
                                setMessage("Akun Anda sudah berhasil didaftar silahkan tekan lanjut untuk masuk ke halaman login")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(context, LoginActivity::class.java)
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
                            Toast.makeText(this, "Akun anda sudah terdaftar!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
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