package com.akmalin.sasahurfoods.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalin.sasahurfoods.data.repository.UserRepository
import com.akmalin.sasahurfoods.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    fun doLogin(email: String, password: String): LiveData<ResultWrapper<Boolean>> {
        return repository.doLogin(email,password).asLiveData(Dispatchers.IO)
    }
}
