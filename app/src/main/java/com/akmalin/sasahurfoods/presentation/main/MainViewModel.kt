package com.akmalin.sasahurfoods.presentation.main

import androidx.lifecycle.ViewModel
import com.akmalin.sasahurfoods.data.datasource.auth.FirebaseAuthDataSource
import com.akmalin.sasahurfoods.data.repository.UserRepository
import com.akmalin.sasahurfoods.data.repository.UserRepositoryImpl
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseServiceImpl

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    constructor() : this(UserRepositoryImpl(FirebaseAuthDataSource(FirebaseServiceImpl())))

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}