package com.akmalin.sasahurfoods.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akmalin.sasahurfoods.data.model.User
import com.akmalin.sasahurfoods.data.repository.UserRepository

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    fun getCurrentUser(): User? {
        return repository.getCurrentUser()
    }

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    fun doLogout(): Boolean {
        return repository.doLogout()
    }
}
