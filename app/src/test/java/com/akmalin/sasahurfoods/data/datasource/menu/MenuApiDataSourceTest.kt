package com.akmalin.sasahurfoods.data.datasource.menu

import com.akmalin.sasahurfoods.data.source.network.model.menu.MenusResponse
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutRequestPayload
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutResponse
import com.akmalin.sasahurfoods.data.source.network.services.FoodAppApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuApiDataSourceTest {
    @MockK
    lateinit var service: FoodAppApiService

    private lateinit var dataSource: MenuDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = MenuApiDataSource(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<MenusResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val actualResponse = dataSource.getMenuData("Food")
            coVerify { service.getMenus(any()) }
            assertEquals(mockResponse, actualResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestPayload>(relaxed = true)
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResponse = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(mockResponse, actualResponse)
        }
    }
}
