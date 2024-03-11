package com.example.pyco.data

import com.example.pyco.data.entities.Category

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getCategoryById(id: Int): Category
    suspend fun getCategoryByName(name: String): Category
}