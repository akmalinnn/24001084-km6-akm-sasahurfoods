package com.akmalin.sasahurfoods.data.datasource.category

import com.akmalin.sasahurfoods.data.source.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategoryData(): CategoriesResponse
}
