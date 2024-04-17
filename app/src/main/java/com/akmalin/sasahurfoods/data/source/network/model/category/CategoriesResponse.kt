package com.akmalin.sasahurfoods.data.source.network.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoriesResponse(
    @SerializedName("code")
    val code: Double?,
    @SerializedName("data")
    val data: List<CategoryItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)