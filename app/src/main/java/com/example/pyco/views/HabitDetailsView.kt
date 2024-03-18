package com.example.pyco.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
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

@Composable
fun HabitDetailsView(habitDetails: HabitDetails) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 3.dp,
        modifier = Modifier.padding(4.dp)
    ) {

        Column(modifier = Modifier.background(colorLightOrange)) {
            Text(
                text = habitDetails.title,
                color = Color.Black,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 20.dp)
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
            Box(
                modifier = Modifier
                    .background(
                        color = colorLightOrange2,
                        shape = RoundedCornerShape(30.dp)
                    ),
            ) {

                Text(
                    text = habitDetails.details,
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
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
                )
            )
        }
    }
}