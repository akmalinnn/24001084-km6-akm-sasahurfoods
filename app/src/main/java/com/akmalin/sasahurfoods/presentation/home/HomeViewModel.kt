package com.akmalin.sasahurfoods.presentation.home

import androidx.lifecycle.ViewModel
import com.akmalin.sasahurfoods.data.repository.CategoryRepository
import com.akmalin.sasahurfoods.data.repository.MenuRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel(){

    fun getMenus() = menuRepository.getMenus()
    fun getCategories() = categoryRepository.getCategories()
}