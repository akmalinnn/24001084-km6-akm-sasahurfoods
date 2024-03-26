package com.akmalin.sasahurfoods.data.datasource.category

import com.akmalin.sasahurfoods.data.model.Category

class DummyCategoryDataSource: CategoryDataSource {
    override fun getCategories(): List<Category> {
        return mutableListOf(
            Category(imgUrl = "https://github.com/akmalinnn/sasahursFood-assets/blob/main/category_img/img_rice_category.png?raw=true", name = "Nasi"),
            Category(imgUrl = "https://github.com/akmalinnn/sasahursFood-assets/blob/main/category_img/img_mie_category.png?raw=true", name = "Mie"),
            Category(imgUrl = "https://github.com/akmalinnn/sasahursFood-assets/blob/main/category_img/img_snack_category.png?raw=true", name = "Snack"),
            Category(imgUrl = "https://github.com/akmalinnn/sasahursFood-assets/blob/main/category_img/img_drink_category.png?raw=true", name = "Minuman")
        )
    }
}