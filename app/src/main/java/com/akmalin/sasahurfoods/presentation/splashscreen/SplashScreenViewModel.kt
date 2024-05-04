package com.akmalin.sasahurfoods.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.akmalin.sasahurfoods.data.repository.UserRepository

class SplashScreenViewModel(private val repository: UserRepository) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}
