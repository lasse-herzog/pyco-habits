package com.example.pyco.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.R
import com.example.pyco.data.Category
import com.example.pyco.data.CategorySampleData
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.viewmodels.HabitsOverviewViewModel
import com.example.pyco.views.ui.theme.PycoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsOverviewScreen(viewModel: HabitsOverviewViewModel = hiltViewModel()){
    val habits = viewModel.uiState.collectAsState().value.habits
    var sortAscending by remember{mutableStateOf(true)};
    var sortNewest by remember {mutableStateOf(true)}
    //TODO: implement route for detail view
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "My Habits",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.sortHabitsAlphabetical(sortAscending)
                            sortAscending = !sortAscending
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_sort_by_alpha_24),
                                contentDescription = "Sort in alphabetical order"
                            )
                        }
                        IconButton(onClick = {
                            viewModel.sortHabitsNewest(sortNewest)
                            sortNewest = !sortNewest
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_new_releases_24),
                                contentDescription = "Newest first"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { /* TODO: open detail screen for habit creation */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                CategoryFilterList(CategorySampleData.catSample)
                HabitsList(habits)
            }
        }
}

@Composable
fun CatFilterChip(categoryName: String){
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        selected = selected,
        onClick = { selected = !selected /*TODO sort function*/ },
        label = {Text(categoryName)},
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
fun HabitsList(habits: List<HabitAndHabitBlueprintWithCategories>) {
    LazyColumn {
        items(habits) { habit ->
            HabitItem(habit = habit)
        }
    }
}

@Composable
fun CategoryFilterList(categories: List<Category>){
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(categories){cat ->
            CatFilterChip(categoryName = cat.name)
        }
    }
}

@Preview
@Composable
fun PreviewHabitsOverviewScreen() {
    PycoTheme {
        HabitsOverviewScreen()
    }
}