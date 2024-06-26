package com.akmalin.sasahurfoods.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalin.sasahurfoods.data.model.User
import com.akmalin.sasahurfoods.data.repository.CategoryRepository
import com.akmalin.sasahurfoods.data.repository.MenuRepository
import com.akmalin.sasahurfoods.data.repository.UserRepository
import com.akmalin.sasahurfoods.data.source.local.pref.UserPreference
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userPreference: UserPreference,
    private val repository: UserRepository,
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(userPreference.isUsingGridMode())
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun getListMode(): Int {
        return if (userPreference.isUsingGridMode()) 1 else 0
    }

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        userPreference.setUsingGridMode(!currentValue)
    }

    fun getMenus(categoryName: String? = null) = menuRepository.getMenus(categoryName).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

    fun getCurrentUser(): User? {
        return repository.getCurrentUser()
    }

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}
