package com.example.pyco.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pyco.R
import com.example.pyco.viewmodels.CreateHabitViewModel
import com.example.pyco.views.ui.theme.PycoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHabit(navController: NavHostController, viewModel: CreateHabitViewModel) {
    // State variables for text fields and dropdown
    var titleText by remember { mutableStateOf("Hier den Habit Namen eingeben") }
    var descriptionText by remember {
        mutableStateOf(
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua."
        )
    }
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Leben", "Ernährung", "Test1", "Test2") // Add your tags here
    var selectedIndex by remember { mutableIntStateOf(0) }
    val colorLightOrange = Color(0xFFFFE0B9)
    val colorLightOrange2 = Color(0xFFFFA0B1)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Detailansicht",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
            Column (Modifier.padding(20.dp)) {
                Box(
                    modifier = Modifier
                        .background(
                            color = colorLightOrange2,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(10.dp)

                ) {
                    BasicTextField(
                        value = titleText,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        onValueChange = { titleText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .background(
                                colorLightOrange2,
                                shape = RoundedCornerShape(20.dp)
                            ) // Change as needed for theming
                    )
                }
            }
            Row {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
                ) {
                    Image(
                        painter = painterResource(R.mipmap.ic_habit_icon),
                        contentDescription = "Placeholder icon",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .shadow(
                                elevation = 1.dp,
                                shape = CircleShape,
                                ambientColor = MaterialTheme.colorScheme.surface
                            )
                    )
                }

                Spacer(modifier = Modifier.width(25.dp))


                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    },
                    modifier = Modifier.padding(20.dp)
                ) {
                    TextField(
                        readOnly = true,
                        value = items[selectedIndex],
                        onValueChange = {},
                        label = { Text("Tag") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        items.forEachIndexed { index, selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedIndex = index
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            Text(
                "Habit Details:",
                color = Color.Black,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorLightOrange2,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(10.dp)

                    ) {
                        BasicTextField(
                            value = descriptionText,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            onValueChange = { descriptionText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp)
                        )
                    }
                }
            }

            // Button to submit the data
            Button(
                onClick = {
                    // Call the ViewModel's submit function with the current state values
                    viewModel.submitData(titleText, items[selectedIndex], descriptionText)
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 25.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorLightOrange,
                    contentColor = Color.Black
                )
            ) {
                Text("Speichern")
            }

        }
    }
}

@Preview
@Composable
fun CreateHabitPreview() {
    PycoTheme {
        Surface {
            CreateHabit(
                navController = rememberNavController(),
                viewModel = CreateHabitViewModel()
            )
        }
    }
}