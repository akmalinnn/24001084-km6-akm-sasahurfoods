package com.akmalin.sasahurfoods.data.mapper

import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.data.source.network.model.menu.MenuItemResponse


fun MenuItemResponse?.toMenu() = Menu(
    imgUrl = this?.imgUrl.orEmpty(),
    location = this?.restoAddress.orEmpty(),
    locUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
    price = this?.price ?: 0.0,
    name = this?.name.orEmpty(),
    desc = this?.detail.orEmpty()
)

fun Collection<MenuItemResponse>?.toMenus() = this?.map {
    it.toMenu()
} ?: listOf()