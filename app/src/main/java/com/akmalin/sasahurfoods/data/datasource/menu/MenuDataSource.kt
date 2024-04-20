package com.akmalin.sasahurfoods.data.datasource.menu

import com.akmalin.sasahurfoods.data.source.network.model.menu.MenusResponse
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutRequestPayload
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutResponse

interface MenuDataSource {
    suspend fun getMenuData(categorySlug: String? = null): MenusResponse

    suspend fun createOrder ( payload: CheckoutRequestPayload) : CheckoutResponse
}
