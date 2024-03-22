package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pyco.data.HabitBlueprintsRepository
import com.example.pyco.data.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class CreateHabitViewModel @Inject constructor (
    private val habitsRepository: HabitsRepository,
    private val habitBlueprintsRepository: HabitBlueprintsRepository
): ViewModel() {
    suspend fun submitData(title: String, tag: String, details: String) {
        // Here you would handle the data, e.g., sending to a database or another service
        println("Title: $title")
        println("Tag: $tag")
        println("Details: $details")
    }
}