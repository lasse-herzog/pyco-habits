package com.example.pyco.data

import com.example.pyco.data.daos.CategoryDao
import com.example.pyco.data.entities.Category
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesDataSource: CategoryDao
): CategoriesRepository {
    override suspend fun getCategories(): List<Category> {
        return categoriesDataSource.getAll()
    }

    override suspend fun getCategoryById(id: Int): Category {
        return categoriesDataSource.getById(id)
    }

    override suspend fun getCategoryByName(name: String): Category {
        return categoriesDataSource.getByName(name)
    }

    override suspend fun getCategoriesForChips(): MutableList<CategoryChipAndState> {
        val categoriesWithState: MutableList<CategoryChipAndState> = mutableListOf()
        val categories: List<Category> = categoriesDataSource.getAll()
        categories.forEach {
            categoriesWithState.add(CategoryChipAndState(it, false))
        }
        return categoriesWithState
    }
}