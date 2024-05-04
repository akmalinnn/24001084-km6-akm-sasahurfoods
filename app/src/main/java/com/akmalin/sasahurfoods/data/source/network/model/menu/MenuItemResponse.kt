package com.akmalin.sasahurfoods.data.source.network.model.menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MenuItemResponse(
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("image_url")
    val imgUrl: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("alamat_resto")
    val restoAddress: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("harga_format")
    val priceFormat: String?,
)
