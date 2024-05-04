package com.akmalin.sasahurfoods.presentation.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akmalin.sasahurfoods.databinding.ActivitySplashBinding
import com.akmalin.sasahurfoods.presentation.auth.login.LoginActivity
import com.akmalin.sasahurfoods.presentation.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val splashScreenViewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSplashScreen()
    }

    private fun setSplashScreen() {
        lifecycleScope.launch {
            delay(2000)
            if (splashScreenViewModel.isLoggedIn()) {
                navigateToMain()
            } else {
                navigateToLogin()
            }
        }
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
