package com.akmalin.sasahurfoods.data.repository

import com.akmalin.sasahurfoods.data.datasource.menu.MenuDataSource
import com.akmalin.sasahurfoods.data.mapper.toMenus
import com.akmalin.sasahurfoods.data.model.Cart
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutItemPayload
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutRequestPayload
import com.akmalin.sasahurfoods.utils.ResultWrapper
import com.akmalin.sasahurfoods.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(categorySlug: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(menu: List<Cart>): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(categorySlug: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenuData(categorySlug).data.toMenus()
        }
    }

    override fun createOrder(menu: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            delay(2000)
            dataSource.createOrder(
                CheckoutRequestPayload(
                    total = null,
                    username = null,
                    orders =
                        menu.map {
                            CheckoutItemPayload(
                                catatan = it.itemNotes,
                                harga = it.menuPrice,
                                nama = it.menuName,
                                qty = it.itemQuantity,
                            )
                        },
                ),
            ).status ?: false
        }
    }
}
