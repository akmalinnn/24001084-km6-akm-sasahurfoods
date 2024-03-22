package com.example.a24001084_km6_akm_warungpenyet_ch2.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class Category(
    var id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
    var name: String,
    )