package com.dicoding.sewez.ui

import com.dicoding.sewez.R

data class CategoryItem(val name: String, val imageResId: Int)

object DummyData {
    val categories = listOf(
        CategoryItem("Item 1", R.drawable.item1),
        CategoryItem("Item 2", R.drawable.item2),
        CategoryItem("Item 3", R.drawable.item3),
        CategoryItem("Item 4", R.drawable.item4),
        CategoryItem("Item 5", R.drawable.item5)
    )
}