package com.example.pyco.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pyco.R
import com.example.pyco.views.ui.theme.PycoTheme

data class HabitDetails(
    val title: String,
    val icon: String,
    val interval: String,
    val details: String,
    val tags: List<String>,
    val tagCount: Int
)

val colorLightOrange = Color(0xFFFFE0B9)
val colorLightOrange2 = Color(0xFFFFA0B1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailsView(habitDetails: HabitDetails, navController: NavHostController) {
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
                .background(colorLightOrange)
                .padding(innerPadding)
                .fillMaxHeight()
        ) {
            Text(
                text = habitDetails.title,
                color = Color.Black,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
            )

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

                Box(
                    modifier = Modifier
                        .background(
                            color = colorLightOrange2,
                            shape = RoundedCornerShape(30.dp)
                        )
                ) {

                    Column {
                        Text(
                            text = "Tags:",
                            color = Color.White,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 40.dp)
                        )
                        Text(
                            text = habitDetails.tags.component1(),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 70.dp)
                        )
                        Text(
                            text = habitDetails.tags.component2(),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 70.dp)
                        )
                    }
                }
            }

            Text(
                text = "Habit Details:",
                color = Color.Black,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
            )
            Column(Modifier.padding(20.dp)) {

                Box(
                    modifier = Modifier
                        .background(
                            color = colorLightOrange2,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(30.dp)

                ) {

                    Text(
                        text = habitDetails.details,
                        color = Color.White,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(20.dp)

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HabitDetailsViewPreview() {
    PycoTheme {
        Surface {
            HabitDetailsView(
                habitDetails = HabitDetails(
                    "Gesundes Essen kochen",
                    "TestIcon",
                    "2 Tage",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.",
                    listOf("Leben", "Ernärung"),
                    3
                ), navController = rememberNavController()
            )
        }
    }
}