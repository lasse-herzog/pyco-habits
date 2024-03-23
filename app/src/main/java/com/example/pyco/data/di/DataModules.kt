package com.example.pyco.data.di

import android.content.Context
import androidx.room.Room
import com.example.pyco.data.repositories.CategoriesRepository
import com.example.pyco.data.repositories.CategoriesRepositoryImpl
import com.example.pyco.data.repositories.HabitBlueprintsRepository
import com.example.pyco.data.repositories.HabitBlueprintsRepositoryImpl
import com.example.pyco.data.repositories.HabitsRepository
import com.example.pyco.data.repositories.HabitsRepositoryImpl
import com.example.pyco.data.PycoDatabase
import com.example.pyco.data.repositories.QuotesRepository
import com.example.pyco.data.repositories.QuotesRepositoryImpl
import com.example.pyco.data.daos.CategoryDao
import com.example.pyco.data.daos.HabitBlueprintDao
import com.example.pyco.data.daos.HabitDao
import com.example.pyco.data.daos.HabitDateDao
import com.example.pyco.data.daos.QuoteDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Modules to provide functionality to the data layer.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindCategoriesRepository(repository: CategoriesRepositoryImpl): CategoriesRepository

    @Singleton
    @Binds
    abstract fun bindHabitsRepository(repository: HabitsRepositoryImpl): HabitsRepository

    @Singleton
    @Binds
    abstract fun binHabitBlueprintsRepository(repository: HabitBlueprintsRepositoryImpl): HabitBlueprintsRepository

    @Singleton
    @Binds
    abstract fun bindQuotesRepository(repository: QuotesRepositoryImpl): QuotesRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): PycoDatabase {
        return Room
            .databaseBuilder(context.applicationContext, PycoDatabase::class.java, "PYCO.db")
            .createFromAsset("PYCO_db.db")
            .build()
    }

    @Provides
    fun provideCategoryDao(database: PycoDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideHabitDao(database: PycoDatabase): HabitDao = database.habitDao()

    @Provides
    fun provideHabitBlueprintDao(database: PycoDatabase): HabitBlueprintDao =
        database.habitBlueprintDao()

    @Provides
    fun provideHabitDateDao(database: PycoDatabase): HabitDateDao = database.habitDateDao()

    @Provides
    fun provideQuoteDao(database: PycoDatabase): QuoteDao = database.quoteDao()
}
