package com.example.a24001084_km6_akm_warungpenyet_ch2.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import java.util.UUID


data class Menu(
    var id: String = UUID.randomUUID().toString(),
    var imgUrl : String,
    var name: String,
    var price: Double,
    var desc: String,
    var location: String,
    var locUrl: String
    )

