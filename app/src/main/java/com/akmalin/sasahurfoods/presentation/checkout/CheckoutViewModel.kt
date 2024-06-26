package com.akmalin.sasahurfoods.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.akmalin.sasahurfoods.data.repository.CartRepository
import com.akmalin.sasahurfoods.data.repository.MenuRepository
import com.akmalin.sasahurfoods.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository,
) : ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun removeItemCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAll()
        }
    }

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }

    fun checkoutCart() = menuRepository.createOrder(checkoutData.value?.payload?.first.orEmpty()).asLiveData(Dispatchers.IO)
}
