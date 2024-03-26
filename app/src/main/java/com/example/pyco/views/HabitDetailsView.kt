package com.example.pyco.views

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.data.CategoryChipAndState
import com.example.pyco.data.entities.Category
import com.example.pyco.viewmodels.HabitDetailsViewViewModel
import java.util.Calendar

private var selectedCategories: List<Category> = listOf()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailsView(viewModel: HabitDetailsViewViewModel = hiltViewModel(), onNavigateUp: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var datum by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var isBadHabit by remember { mutableStateOf(false) }
    var checkBoxStatus by remember { mutableStateOf(false) }
    val pattern = remember { Regex("^[1-9][0-9]{0,2}\$") }
    val context = LocalContext.current
    val categories = viewModel.uiState.collectAsState().value.categories

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
                CategoryListDetails(categories, viewModel)
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
                                    onValueChange = { newValue ->
                                        if (newValue.isEmpty() || newValue.matches(pattern)) {
                                            interval = newValue
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Es dürfen nur ganze Zahlen von 1 bis 999 eingegeben werden!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Checkbox(
                                checked = checkBoxStatus,
                                onCheckedChange = { isChecked ->
                                    checkBoxStatus = isChecked
                                    showDialog = isChecked
                                }
                            )
                        }
                        Column {

                            Text(text = "End-Datum setzen?")

                            if (showDialog) {
                                val calendar = Calendar.getInstance()
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH)
                                val day = calendar.get(Calendar.DAY_OF_MONTH)

                                val datePickerDialog = DatePickerDialog(
                                    context,
                                    { _, year, monthOfYear, dayOfMonth ->
                                        datum = "$dayOfMonth.${monthOfYear + 1}.$year"
                                        showDialog = false
                                    }, year, month, day
                                )

                                datePickerDialog.setOnCancelListener {
                                    showDialog = false
                                    checkBoxStatus = false
                                }

                                datePickerDialog.show()
                            }

                            if (datum.isNotEmpty() && checkBoxStatus) {
                                Text(text = "Ausgewähltes Datum: $datum")
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
                            value = descriptionText,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            onValueChange = { descriptionText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 300.dp)
                                .padding(30.dp)
                                .wrapContentHeight(align = Alignment.CenterVertically)
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
                        name,
                        selectedCategories,
                        descriptionText,
                        isBadHabit,
                        interval.toInt(),
                        datum
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
                Text("Speichern")
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
fun CategoryListDetails(categories: List<CategoryChipAndState>, viewModel: HabitDetailsViewViewModel) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categories) { cat ->
            CatChipDetails(cat, viewModel)
        }
    }
}