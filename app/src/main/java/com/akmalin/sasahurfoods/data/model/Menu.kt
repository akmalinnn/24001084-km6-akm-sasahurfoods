package com.akmalin.sasahurfoods.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id: String? = UUID.randomUUID().toString(),
    var imgUrl : String,
    var name: String,
    var price: Double,
    var desc: String,
    var location: String,
    var locUrl: String,
    var rating: Double
    ): Parcelable

