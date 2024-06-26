package com.akmalin.sasahurfoods.data.repository

import com.akmalin.sasahurfoods.data.datasource.auth.AuthDataSource
import com.akmalin.sasahurfoods.data.datasource.user.UserDataSource
import com.akmalin.sasahurfoods.data.model.User
import com.akmalin.sasahurfoods.utils.ResultWrapper
import com.akmalin.sasahurfoods.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

interface UserRepository {
    @Throws(exceptionClasses = [Exception::class])
    fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>>

    @Throws(exceptionClasses = [Exception::class])
    fun doRegister(
        username: String,
        email: String,
        password: String,
        numberPhone: String,
    ): Flow<ResultWrapper<Boolean>>

    fun updateProfile(username: String? = null): Flow<ResultWrapper<Boolean>>

    fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>>

    fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>>

    fun requestChangePasswordByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): User?

    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserRepositoryImpl(
    private val dataSource: AuthDataSource,
    private val userDataSource: UserDataSource,
) : UserRepository {
    override fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doLogin(email, password) }
    }

    override fun doRegister(
        username: String,
        email: String,
        password: String,
        numberPhone: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.doRegister(
                username = username,
                email = email,
                password = password,
                numberPhone = numberPhone,
            )
        }
    }

    override fun isUsingGridMode(): Boolean {
        return userDataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        return userDataSource.setUsingGridMode(isUsingGridMode)
    }

    override fun updateProfile(username: String?): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateProfile(username = username) }
    }

    override fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updatePassword(newPassword) }
    }

    override fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateEmail(newEmail) }
    }

    override fun requestChangePasswordByEmail(): Boolean {
        return dataSource.requestChangePasswordByEmail()
    }

    override fun doLogout(): Boolean {
        return dataSource.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return dataSource.getCurrentUser()
    }
}
