package com.akmalin.sasahurfoods.di

import android.content.SharedPreferences
import com.akmalin.sasahurfoods.data.datasource.auth.AuthDataSource
import com.akmalin.sasahurfoods.data.datasource.auth.FirebaseAuthDataSource
import com.akmalin.sasahurfoods.data.datasource.cart.CartDataSource
import com.akmalin.sasahurfoods.data.datasource.cart.CartDatabaseDataSource
import com.akmalin.sasahurfoods.data.datasource.category.CategoryApiDataSource
import com.akmalin.sasahurfoods.data.datasource.category.CategoryDataSource
import com.akmalin.sasahurfoods.data.datasource.menu.MenuApiDataSource
import com.akmalin.sasahurfoods.data.datasource.menu.MenuDataSource
import com.akmalin.sasahurfoods.data.datasource.user.UserDataSource
import com.akmalin.sasahurfoods.data.datasource.user.UserDataSourceImpl
import com.akmalin.sasahurfoods.data.repository.CartRepository
import com.akmalin.sasahurfoods.data.repository.CartRepositoryImpl
import com.akmalin.sasahurfoods.data.repository.CategoryRepository
import com.akmalin.sasahurfoods.data.repository.CategoryRepositoryImpl
import com.akmalin.sasahurfoods.data.repository.MenuRepository
import com.akmalin.sasahurfoods.data.repository.MenuRepositoryImpl
import com.akmalin.sasahurfoods.data.repository.UserRepository
import com.akmalin.sasahurfoods.data.repository.UserRepositoryImpl
import com.akmalin.sasahurfoods.data.source.local.database.AppDatabase
import com.akmalin.sasahurfoods.data.source.local.database.dao.CartDao
import com.akmalin.sasahurfoods.data.source.local.pref.UserPreference
import com.akmalin.sasahurfoods.data.source.local.pref.UserPreferenceImpl
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseService
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseServices
import com.akmalin.sasahurfoods.data.source.network.services.FoodAppApiService
import com.akmalin.sasahurfoods.presentation.auth.login.LoginViewModel
import com.akmalin.sasahurfoods.presentation.auth.register.RegisterViewModel
import com.akmalin.sasahurfoods.presentation.cart.CartViewModel
import com.akmalin.sasahurfoods.presentation.checkout.CheckoutViewModel
import com.akmalin.sasahurfoods.presentation.detailfood.DetailMenuViewModel
import com.akmalin.sasahurfoods.presentation.home.HomeViewModel
import com.akmalin.sasahurfoods.presentation.main.MainViewModel
import com.akmalin.sasahurfoods.presentation.profile.ProfileViewModel
import com.akmalin.sasahurfoods.presentation.splashscreen.SplashScreenViewModel
import com.akmalin.sasahurfoods.utils.SharedPreferenceUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {
    private val networkModule =
        module {
            single<FoodAppApiService> {
                FoodAppApiService.invoke()
            }
        }

    private val localModule =
        module {
            single<AppDatabase> {
                AppDatabase.createInstance(androidContext())
            }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(androidContext(), UserPreferenceImpl.PREF_NAME)
            }
            single<UserPreference> {
                UserPreferenceImpl(get())
            }
        }

    private val firebaseModule =
        module {
            single { FirebaseAuth.getInstance() }

            single<FirebaseAuth> {
                FirebaseAuth.getInstance()
            }
            single<FirebaseService> {
                FirebaseServices(get())
            }
        }

    private val dataSourceModule =
        module {
            single<AuthDataSource> {
                FirebaseAuthDataSource(get())
            }
            single<CartDataSource> {
                CartDatabaseDataSource(get())
            }
            single<MenuDataSource> {
                MenuApiDataSource(get())
            }
            single<CategoryDataSource> {
                CategoryApiDataSource(get())
            }
            single<UserDataSource> {
                UserDataSourceImpl(get())
            }
        }

    private val repositoryModule =
        module {
            single<CartRepository> {
                CartRepositoryImpl(get())
            }
            single<MenuRepository> {
                MenuRepositoryImpl(get())
            }
            single<CategoryRepository> {
                CategoryRepositoryImpl(get())
            }
            single<UserRepository> {
                UserRepositoryImpl(get())
            }
            single<UserRepository> {
                UserRepositoryImpl(get())
            }
        }

    private val viewModelModule =
        module {

            viewModelOf(::LoginViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::SplashScreenViewModel)
            viewModelOf(::CheckoutViewModel)
            viewModel { params ->
                DetailMenuViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
        }

    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            firebaseModule,
            dataSourceModule,
            repositoryModule,
            viewModelModule,
        )
}
