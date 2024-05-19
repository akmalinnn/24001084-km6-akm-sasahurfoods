package com.akmalin.sasahurfoods.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.akmalin.sasahurfoods.data.model.User
import com.akmalin.sasahurfoods.data.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProfileViewModel
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = ProfileViewModel(repository)
    }

    @Test
    fun `get current user returns correct user`() {
        val user = User("1", "Janu ", "janu112@gmail.com")
        every { repository.getCurrentUser() } returns user
        val result = viewModel.getCurrentUser()
        assertEquals(user, result)
    }

    @Test
    fun `toggle edit mode switches correctly`() {
        val observer: Observer<Boolean> = mockk(relaxed = true)

        viewModel.isEditMode.observeForever(observer)
        viewModel.changeEditMode()
        verify { observer.onChanged(true) }
        viewModel.changeEditMode()
        verify { observer.onChanged(false) }
    }

    @Test
    fun `check if user is logged in`() {
        val isLoggedIn = true
        every { repository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }

    @Test
    fun `logout calls repository`() {
        val doLogout = true
        every { repository.doLogout() } returns doLogout
        val result = viewModel.doLogout()
        assertEquals(doLogout, result)
    }
}
