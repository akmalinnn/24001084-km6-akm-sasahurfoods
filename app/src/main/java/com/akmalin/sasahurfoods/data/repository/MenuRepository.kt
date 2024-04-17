package com.akmalin.sasahurfoods.data.repository

import com.akmalin.sasahurfoods.data.datasource.menu.MenuDataSource
import com.akmalin.sasahurfoods.data.mapper.toMenus
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.utils.ResultWrapper
import com.akmalin.sasahurfoods.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(categorySlug : String? = null) : Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(categorySlug: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenuData(categorySlug).data.toMenus()
        }
    }

}