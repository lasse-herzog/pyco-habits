package com.example.pyco.data.dependency_injection

import android.content.Context
import androidx.room.Room
import com.example.pyco.data.HabitsRepository
import com.example.pyco.data.HabitsRepositoryImpl
import com.example.pyco.data.PycoDatabase
import com.example.pyco.data.daos.HabitDao
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
abstract  class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindHabitsRepository(repository: HabitsRepositoryImpl) : HabitsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): PycoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PycoDatabase::class.java,
            "PYCO.db"
        ).build()
    }

    @Provides
    fun provideHabitDao(database: PycoDatabase): HabitDao = database.habitDao()
}
