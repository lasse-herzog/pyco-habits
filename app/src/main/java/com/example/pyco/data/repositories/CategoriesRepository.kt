package com.example.pyco.data.repositories

import com.example.pyco.data.CategoryChipAndState
import com.example.pyco.data.entities.Category

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getCategoriesForChips(): MutableList<CategoryChipAndState>
    suspend fun getCategoryById(id: Int): Category
    suspend fun getCategoryByName(name: String): Category
}