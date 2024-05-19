package com.akmalin.sasahurfoods.data.datasource.auth

import com.akmalin.sasahurfoods.data.model.User
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseServices
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceImplTest {
    @MockK
    lateinit var service: FirebaseServices

    private lateinit var dataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val email: String = "janu112@gmail.com"
            val password: String = "akufgo"
            coEvery { service.doLogin(any(), any()) } returns true
            val result = dataSource.doLogin(email, password)
            coVerify { service.doLogin(any(), any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val name: String = "SatePrancis"
            val email: String = "janu112@gmail.com"
            val password: String = "akufgo"
            val numberPhone: String = "55555"
            coEvery { service.doRegister(any(), any(), any(), any()) } returns true
            val result = dataSource.doRegister(name, email, password, numberPhone)
            coVerify { service.doRegister(any(), any(), any(), any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun `update profile while is null`() {
        runTest {
            coEvery { service.updateProfile(any()) } returns true
            val result = dataSource.updateProfile()
            coVerify { service.updateProfile(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            coEvery { service.updatePassword(any()) } returns true
            val result = dataSource.updatePassword("akufgo")
            coVerify { service.updatePassword(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            coEvery { service.updateEmail(any()) } returns true
            val result = dataSource.updateEmail("akufgo")
            coVerify { service.updateEmail(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun sendChangePasswordRequestByEmail() {
        runTest {
            coEvery { service.requestChangePasswordByEmail() } returns true
            val result = dataSource.requestChangePasswordByEmail()
            coEvery { service.requestChangePasswordByEmail() }
            assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        every { service.doLogout() } returns true
        val result = dataSource.doLogout()
        verify { service.doLogout() }
        assertEquals(true, result)
    }

    @Test
    fun isLoggedIn() {
        every { service.isLoggedIn() } returns true
        val result = dataSource.isLoggedIn()
        verify { service.isLoggedIn() }
        assertEquals(true, result)
    }

    @Test
    fun getCurrentUser() {
        val mockFirebaseUser = mockk<FirebaseUser>()
        val expectedUser =
            User(
                id = "124",
                username = "Janu",
                email = "janu112@gmail.com",
            )

        every { mockFirebaseUser.uid } returns "124"
        every { mockFirebaseUser.displayName } returns "Janu"
        every { mockFirebaseUser.email } returns "janu112@gmail.com"
        every { service.getCurrentUser() } returns mockFirebaseUser

        val result = dataSource.getCurrentUser()

        verify { service.getCurrentUser() }
        assertEquals(expectedUser, result)
    }
}
