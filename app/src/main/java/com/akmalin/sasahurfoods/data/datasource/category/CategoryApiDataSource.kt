package com.akmalin.sasahurfoods.data.datasource.category

import com.akmalin.sasahurfoods.data.source.network.model.category.CategoriesResponse
import com.akmalin.sasahurfoods.data.source.network.services.FoodAppApiService


class CategoryApiDataSource(private val service: FoodAppApiService) : CategoryDataSource {
    override suspend fun getCategoryData(): CategoriesResponse {
        return service.getCategories()
    }



}