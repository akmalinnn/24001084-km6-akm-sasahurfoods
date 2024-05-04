package com.akmalin.sasahurfoods.presentation.main

import androidx.lifecycle.ViewModel
import com.akmalin.sasahurfoods.data.repository.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}
