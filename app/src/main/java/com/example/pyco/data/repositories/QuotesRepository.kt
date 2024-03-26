package com.example.pyco.data.repositories

import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.Quote


interface QuotesRepository {
    suspend fun getQuote(habit: Habit): Quote
    suspend fun getQuoteByCategory(category: Category): Quote?
    suspend fun getQuotesByCategory(category: Category): List<Quote>
    suspend fun getQuoteByHabitBlueprint(habitBlueprint: HabitBlueprint): Quote?
    suspend fun getQuotesByHabitBlueprint(habitBlueprint: HabitBlueprint): List<Quote>

}
