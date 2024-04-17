package com.akmalin.sasahurfoods.data.datasource.menu

import com.akmalin.sasahurfoods.data.source.network.model.menu.MenusResponse

interface MenuDataSource {
    suspend fun getMenuData(categorySlug: String? = null): MenusResponse
}