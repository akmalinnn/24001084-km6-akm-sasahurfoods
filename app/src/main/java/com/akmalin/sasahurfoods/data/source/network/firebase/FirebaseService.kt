package com.akmalin.sasahurfoods.data.source.network.firebase

import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

interface FirebaseService {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        username: String,
        email: String,
        password: String,
        numberPhone: String,
    ): Boolean

    suspend fun updateProfile(username: String? = null): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    suspend fun updateEmail(newEmail: String): Boolean

    fun requestChangePasswordByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): FirebaseUser?
}
