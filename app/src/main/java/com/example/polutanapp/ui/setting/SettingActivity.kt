package com.example.polutanapp.ui.setting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.polutanapp.R
import com.example.polutanapp.ViewModelFactory
import com.example.polutanapp.databinding.ActivitySettingBinding
import com.example.polutanapp.model.UserPreference
import com.example.polutanapp.ui.aboutus.AboutUsFragment
import com.example.polutanapp.ui.auth.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {

    private lateinit var settingBinding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)

        setUpView()
        setUpAction()
    }

    private fun setUpView() {
        supportActionBar?.hide()
        settingBinding.toolbarAboutUs.setNavigationIcon(R.drawable.icon_back)
        settingBinding.toolbarAboutUs.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, AboutUsFragment::class.java))
        }
    }

    private fun setUpAction() {

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SettingViewModel::class.java]

        val switchTheme = settingBinding.switchTheme

        viewModel.getThemeSettings().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        })

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }

        settingBinding.tvLogOut.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Logout")
                setMessage("Are you sure you want to exit?")

                setPositiveButton("Yes") { _, _ ->
                    viewModel.logout()
                    val intent = Intent(context, RegisterActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }

                setNegativeButton("No") { dialog, _ -> dialog.cancel() }

                create()
                show()
            }
        }
    }
}