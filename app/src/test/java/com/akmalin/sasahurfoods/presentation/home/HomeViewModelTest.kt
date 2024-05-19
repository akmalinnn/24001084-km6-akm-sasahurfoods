package com.akmalin.sasahurfoods.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.akmalin.sasahurfoods.data.model.User
import com.akmalin.sasahurfoods.data.repository.CategoryRepository
import com.akmalin.sasahurfoods.data.repository.MenuRepository
import com.akmalin.sasahurfoods.data.repository.UserRepository
import com.akmalin.sasahurfoods.data.source.local.pref.UserPreference
import com.akmalin.sasahurfoods.tools.MainCoroutineRule
import com.akmalin.sasahurfoods.tools.getOrAwaitValue
import com.akmalin.sasahurfoods.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var userPreference: UserPreference

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { userPreference.isUsingGridMode() } returns true
        every { userPreference.setUsingGridMode(any()) } returns Unit
        every { categoryRepository.getCategories() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))))
            }
        viewModel = spyk(HomeViewModel(categoryRepository, menuRepository, userPreference, userRepository))
    }

    @Test
    fun `isUsingGridMode is true`() =
        runTest {
            val expectedValue = true
            val actualValue = viewModel.isUsingGridMode.getOrAwaitValue()
            assertEquals(expectedValue, actualValue)
        }

    @Test
    fun getMenuList() {
        every { menuRepository.getMenus() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))))
            }
        val result = viewModel.getMenus().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun getCategories() {
        val result = viewModel.getCategories().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun changeListMode() =
        runTest {
            val currentValue = viewModel.isUsingGridMode.getOrAwaitValue()
            val expectedNewValue = !currentValue
            viewModel.changeListMode()
            val actualNewValue = viewModel.isUsingGridMode.getOrAwaitValue()
            assertEquals(expectedNewValue, actualNewValue)
        }

    @Test
    fun getUser() {
        val user = User("1", "Janu ", "janu112@gmail.com")
        every { userRepository.getCurrentUser() } returns user
        val result = viewModel.getCurrentUser()
        assertEquals(user, result)
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { userRepository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
