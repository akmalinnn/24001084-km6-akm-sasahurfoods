package com.akmalin.sasahurfoods.data.repository

import com.akmalin.sasahurfoods.data.datasource.category.CategoryDataSource
import com.akmalin.sasahurfoods.data.mapper.toCategories
import com.akmalin.sasahurfoods.data.model.Category
import com.akmalin.sasahurfoods.utils.ResultWrapper
import com.akmalin.sasahurfoods.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategoryData().data.toCategories() }
    }
}
