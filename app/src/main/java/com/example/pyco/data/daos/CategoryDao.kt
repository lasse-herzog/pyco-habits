package com.example.pyco.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.pyco.data.entities.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    suspend fun getById(categoryId: Int): Category

    @Query("SELECT * FROM category WHERE name = :categoryName")
    suspend fun getByName(categoryName: String): Category
}