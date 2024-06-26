package com.akmalin.sasahurfoods.data.repository

import app.cash.turbine.test
import com.akmalin.sasahurfoods.data.datasource.menu.MenuDataSource
import com.akmalin.sasahurfoods.data.model.Cart
import com.akmalin.sasahurfoods.data.source.network.model.menu.MenuItemResponse
import com.akmalin.sasahurfoods.data.source.network.model.menu.MenusResponse
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutResponse
import com.akmalin.sasahurfoods.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    @MockK
    lateinit var dataSource: MenuDataSource

    private lateinit var repository: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = MenuRepositoryImpl(dataSource)
    }

    @Test
    fun `get menus while loading`() {
        val menu1 =
            MenuItemResponse(
                restoAddress = "akwjdhakwjdh",
                detail = "kajhdawklhd",
                price = 10000.0,
                priceFormat = "akjdhakwjdh",
                imgUrl = "akdhwakldhawk",
                name = "akdhaklwjdh",
            )
        val menu2 =
            MenuItemResponse(
                restoAddress = "akwjdhakwjdh",
                detail = "kajhdawklhd",
                price = 10000.0,
                priceFormat = "akjdhakwjdh",
                imgUrl = "akdhwakldhawk",
                name = "akdhaklwjdh",
            )
        val mockListMenu = listOf(menu1, menu2)
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenuData(any()) } returns mockResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `get menus while success`() {
        val menu1 =
            MenuItemResponse(
                restoAddress = "akwjdhakwjdh",
                detail = "kajhdawklhd",
                price = 10000.0,
                priceFormat = "akjdhakwjdh",
                imgUrl = "akdhwakldhawk",
                name = "akdhaklwjdh",
            )
        val menu2 =
            MenuItemResponse(
                restoAddress = "akwjdhakwjdh",
                detail = "kajhdawklhd",
                price = 10000.0,
                priceFormat = "akjdhakwjdh",
                imgUrl = "akdhwakldhawk",
                name = "akdhaklwjdh",
            )
        val mockListMenu = listOf(menu1, menu2)
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenuData(any()) } returns mockResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `get menus while error`() {
        runTest {
            coEvery { dataSource.getMenuData(any()) } throws IllegalStateException("Error")
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `get menus while empty`() {
        val mockListMenu = listOf<MenuItemResponse>()
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenuData(any()) } returns mockResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `create order while loading`() {
        val cart1 =
            Cart(
                id = 1,
                menuId = "asdhawkjdha",
                menuImgUrl = "akjdhakljhkjw",
                menuName = "akjdhwakljdha",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "kajhdakwjdha",
            )

        val cart2 =
            Cart(
                id = 2,
                menuId = "asdhawkjdha",
                menuImgUrl = "akjdhakljhkjw",
                menuName = "akjdhwakljdha",
                menuPrice = 10000.0,
                itemQuantity = 5,
                itemNotes = "kajhdakwjdha",
            )

        val mockResponse =
            CheckoutResponse(
                code = 200,
                message = "ahdakjwdh",
                status = true,
            )

        val mockLisCart = listOf<Cart>(cart1, cart2)
        coEvery { dataSource.createOrder(any()) } returns mockResponse
        runTest {
            repository.createOrder(mockLisCart).map {
                delay(100)
                it
            }.test {
                delay(2110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `create order while success`() {
        val cart1 =
            Cart(
                id = 1,
                menuId = "asdhawkjdha",
                menuImgUrl = "akjdhakljhkjw",
                menuName = "akjdhwakljdha",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "kajhdakwjdha",
            )

        val cart2 =
            Cart(
                id = 2,
                menuId = "asdhawkjdha",
                menuImgUrl = "akjdhakljhkjw",
                menuName = "akjdhwakljdha",
                menuPrice = 10000.0,
                itemQuantity = 5,
                itemNotes = "kajhdakwjdha",
            )

        val mockResponse =
            CheckoutResponse(
                code = 200,
                message = "ahdakjwdh",
                status = true,
            )

        val mockLisCart = listOf<Cart>(cart1, cart2)
        coEvery { dataSource.createOrder(any()) } returns mockResponse
        runTest {
            repository.createOrder(mockLisCart).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(true, data.payload)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `create order while success status null`() {
        val cart1 =
            Cart(
                id = 1,
                menuId = "asdhawkjdha",
                menuImgUrl = "akjdhakljhkjw",
                menuName = "akjdhwakljdha",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "kajhdakwjdha",
            )

        val cart2 =
            Cart(
                id = 2,
                menuId = "asdhawkjdha",
                menuImgUrl = "akjdhakljhkjw",
                menuName = "akjdhwakljdha",
                menuPrice = 10000.0,
                itemQuantity = 5,
                itemNotes = "kajhdakwjdha",
            )

        val mockResponse =
            CheckoutResponse(
                code = 200,
                message = "ahdakjwdh",
                status = null,
            )

        val mockLisCart = listOf<Cart>(cart1, cart2)
        coEvery { dataSource.createOrder(any()) } returns mockResponse
        runTest {
            repository.createOrder(mockLisCart).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(false, data.payload)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `create order while error`() {
        val mockLisCart = listOf<Cart>()
        coEvery { dataSource.createOrder(any()) } throws IllegalStateException("Error")
        runTest {
            repository.createOrder(mockLisCart).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }
}
