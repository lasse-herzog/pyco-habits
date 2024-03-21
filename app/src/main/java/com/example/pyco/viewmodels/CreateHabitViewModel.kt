package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel

class CreateHabitViewModel : ViewModel() {
    fun submitData(title: String, tag: String, details: String) {
        // Here you would handle the data, e.g., sending to a database or another service
        println("Title: $title")
        println("Tag: $tag")
        println("Details: $details")
    }
}