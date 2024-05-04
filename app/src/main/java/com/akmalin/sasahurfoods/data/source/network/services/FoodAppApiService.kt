package com.akmalin.sasahurfoods.data.source.network.services

import com.akmalin.sasahurfoods.BuildConfig
import com.akmalin.sasahurfoods.data.source.network.model.category.CategoriesResponse
import com.akmalin.sasahurfoods.data.source.network.model.menu.MenusResponse
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutRequestPayload
import com.akmalin.sasahurfoods.data.source.network.model.order.CheckoutResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FoodAppApiService {
    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    @GET("listmenu")
    suspend fun getMenus(
        @Query("c") category: String? = null,
    ): MenusResponse

    @POST("order")
    suspend fun createOrder(
        @Body payload: CheckoutRequestPayload,
    ): CheckoutResponse

    companion object {
        @JvmStatic
        operator fun invoke(): FoodAppApiService {
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(FoodAppApiService::class.java)
        }
    }
}
