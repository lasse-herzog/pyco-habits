package com.example.pyco.data.repositories

import com.example.pyco.data.daos.QuoteDao
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.Quote
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val quotesDataSource: QuoteDao
) : QuotesRepository {
    override suspend fun getQuote(habit: Habit): Quote {
        TODO("Not yet implemented")
    }

    override suspend fun getQuoteByCategory(category: Category): Quote? {
        return quotesDataSource.getQuoteByCategoryId(category.categoryId)
    }

    override suspend fun getQuotesByCategory(category: Category): List<Quote> {
        return quotesDataSource.getQuotesByCategoryId(category.categoryId)
    }

    override suspend fun getQuoteByHabitBlueprint(habitBlueprint: HabitBlueprint): Quote? {
        return quotesDataSource.getQuoteByHabitBlueprintId(habitBlueprint.habitBlueprintId)
    }

    override suspend fun getQuotesByHabitBlueprint(habitBlueprint: HabitBlueprint): List<Quote> {
        return quotesDataSource.getQuotesByHabitBlueprintId(habitBlueprint.habitBlueprintId)
    }

}
