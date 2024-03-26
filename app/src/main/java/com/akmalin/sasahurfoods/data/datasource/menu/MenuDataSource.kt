package com.akmalin.sasahurfoods.data.datasource.menu

import com.akmalin.sasahurfoods.data.model.Menu

interface MenuDataSource {
    fun getMenus(): List<Menu>
}