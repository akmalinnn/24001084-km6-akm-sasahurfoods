package com.akmalin.sasahurfoods.data.repository

import com.akmalin.sasahurfoods.data.datasource.menu.MenuDataSource
import com.akmalin.sasahurfoods.data.model.Menu

interface MenuRepository {
    fun getMenus(): List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(): List<Menu> = dataSource.getMenus()

}