package com.akmalin.sasahurfoods.data.datasource.category

import com.akmalin.sasahurfoods.data.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}
