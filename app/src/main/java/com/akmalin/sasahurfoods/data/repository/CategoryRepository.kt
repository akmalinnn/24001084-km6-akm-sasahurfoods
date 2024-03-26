package com.akmalin.sasahurfoods.data.repository

import com.akmalin.sasahurfoods.data.datasource.category.CategoryDataSource
import com.akmalin.sasahurfoods.data.model.Category

interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository{
    override fun getCategories(): List<Category> = dataSource.getCategories()
}