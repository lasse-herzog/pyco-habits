package com.example.pyco.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.data.CategoryChipAndState
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.viewmodels.HabitDetailsViewViewModel

private var selectedCategories: List<Category> = listOf()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailsView(
    viewModel: HabitDetailsViewViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    habitId: Int
) {
    var habit: HabitAndHabitBlueprintWithCategories =
        viewModel.uiState.collectAsState().value.habit
    var name by remember { mutableStateOf("") }
    var datum by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isBadHabit by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val categories = viewModel.uiState.collectAsState().value.categories

    name = habit.habitAndHabitBlueprint.habitBlueprint.name
    datum = habit.habitAndHabitBlueprint.habit.end.toString()
    interval = habit.habitAndHabitBlueprint.habit.interval.toString()
    description = habit.habitAndHabitBlueprint.habitBlueprint.description
    isBadHabit = habit.habitAndHabitBlueprint.habitBlueprint.badHabit
    var start = habit.habitAndHabitBlueprint.habit.start
    var end = habit.habitAndHabitBlueprint.habit.end

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        "Habit ansehen und bearbeiten",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.padding(top = 15.dp, start = 20.dp, end = 20.dp)) {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(10.dp)

                ) {
                    BasicTextField(
                        value = name,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        onValueChange = { name = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        maxLines = 1,
                        decorationBox = { innerTextField ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,

                                ) {
                                Text(
                                    text = "Habit Bezeichnung:",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                innerTextField()
                            }
                        }
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CategoryListDetails(categories, habit.categories, viewModel)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row {
                        Column(Modifier.padding(top = 15.dp, start = 20.dp, end = 20.dp)) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(10.dp)

                            ) {
                                BasicTextField(
                                    value = interval,
                                    enabled = false,
                                    onValueChange = {
                                    },
                                    textStyle = TextStyle.Default.copy(
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            shape = RoundedCornerShape(20.dp)
                                        ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    decorationBox = { innerTextField ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,

                                            ) {
                                            Text(
                                                text = "Intevall:",
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                            innerTextField()
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.padding(top = 15.dp, start = 20.dp, end = 20.dp)) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(10.dp)

                            ) {
                                BasicTextField(
                                    value = datum,
                                    enabled = false,
                                    onValueChange = {
                                    },
                                    textStyle = TextStyle.Default.copy(
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            shape = RoundedCornerShape(20.dp)
                                        ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    decorationBox = { innerTextField ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,

                                            ) {
                                            Text(
                                                text = "End-Datum:",
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                            innerTextField()
                                        }
                                    }
                                )
                            }
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Checkbox(
                                checked = isBadHabit,
                                onCheckedChange = { isChecked ->
                                    isBadHabit = isChecked
                                }
                            )
                        }
                        Column {
                            Text(text = "Bad-Habit?")
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(10.dp)
                            .defaultMinSize(minHeight = 300.dp)
                            .clickable { focusRequester.requestFocus() }
                    ) {
                        Text(
                            "Habit Details:",
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        BasicTextField(
                            value = description,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            onValueChange = { description = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp)
                                .defaultMinSize(minHeight = 300.dp)
                                .wrapContentHeight(align = Alignment.CenterVertically)
                                .focusRequester(focusRequester)
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (interval.isEmpty()) {
                        interval = "1"
                    }
                    viewModel.submitData(
                        habitId = habit.habitAndHabitBlueprint.habit.habitId,
                        habitBlueprintId =  habit.habitAndHabitBlueprint.habit.habitBlueprintId,
                        name = name,
                        categories = selectedCategories,
                        description = description,
                        isBadHabit =  isBadHabit,
                        interval = habit.habitAndHabitBlueprint.habit.interval,
                        endDate = end,
                        start = start
                    )
                    onNavigateUp()
                },
                enabled = name.isNotEmpty(),
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 25.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Änderungen speichern")
            }
        }
    }
}

@Composable
fun CatChipDetails(category: CategoryChipAndState, viewModel: HabitDetailsViewViewModel) {
    var selected by remember { mutableStateOf(category.selected) }

    FilterChip(
        selected = selected,
        onClick = {
            selected = !selected
            if ((selectedCategories.isEmpty() || !selectedCategories.contains(category.category)) && selected) {
                selectedCategories = selectedCategories.plus(category.category)
            }
            if (selectedCategories.contains(category.category) && !selected) {
                selectedCategories = selectedCategories.minus(category.category)
            }
            viewModel.categoryClicked(category, selected)
        },
        label = { Text(category.category.name) },
        modifier = Modifier.padding(horizontal = 5.dp),
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Check icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}

@Composable
fun CategoryListDetails(
    categories: List<CategoryChipAndState>,
    clickedCategories: List<Category>,
    viewModel: HabitDetailsViewViewModel
) {
    for (category in categories) {
        for (clickedCategory in clickedCategories) {
            if (category.category.categoryId == clickedCategory.categoryId) {
                category.selected = true
                viewModel.categoryClicked(category, true)
            }
        }
    }

    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categories) { cat ->
            CatChipDetails(cat, viewModel)
        }
    }
}