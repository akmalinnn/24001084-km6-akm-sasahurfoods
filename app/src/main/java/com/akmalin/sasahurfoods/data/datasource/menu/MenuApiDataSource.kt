package com.akmalin.sasahurfoods.data.datasource.menu

import com.akmalin.sasahurfoods.data.source.network.model.menu.MenusResponse
import com.akmalin.sasahurfoods.data.source.network.services.FoodAppApiService


class MenuApiDataSource(private val service : FoodAppApiService): MenuDataSource {
    override suspend fun getMenuData(categorySlug: String?): MenusResponse {
        return service.getMenus(categorySlug)
    }

}