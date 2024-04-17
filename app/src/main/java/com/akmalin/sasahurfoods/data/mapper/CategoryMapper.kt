package com.akmalin.sasahurfoods.data.mapper

import com.akmalin.sasahurfoods.data.model.Category
import com.akmalin.sasahurfoods.data.source.network.model.category.CategoryItemResponse
import java.util.UUID

fun CategoryItemResponse?.toCategory() = Category(
    imgUrl = this?.imgUrl.orEmpty(),
    name = this?.name.orEmpty()
)
fun Collection<CategoryItemResponse>?.toCategories() = this?.map { it.toCategory() } ?: listOf()