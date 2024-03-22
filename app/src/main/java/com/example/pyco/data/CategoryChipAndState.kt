package com.example.pyco.data

import com.example.pyco.data.entities.Category

/**
 * mutable model class for a Category filter chip.
 *
 * @param category the category of the filter chip
 * @param selected the state of the filter chip
 */
data class CategoryChipAndState internal constructor(
    val category: Category,
    var selected: Boolean = false
)
