package com.example.pyco.views

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pyco.views.ui.theme.PycoTheme

@Composable
fun QuoteCard() {
    Card {
        Text(
            text = "Your Mom is beautiful!",
            color = MaterialTheme.colorScheme.primary,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(dailyHabits: List<String>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        QuoteCard()
        
        dailyHabits.forEach{
            val dismissState = rememberSwipeToDismissBoxState()
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            SwipeToDismissBoxValue.Settled -> Color.LightGray
                            SwipeToDismissBoxValue.StartToEnd -> Color.Green
                            SwipeToDismissBoxValue.EndToStart -> Color.Red
                        }, label = ""
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color))
                }
            ) {
                Card {
                    ListItem(
                        headlineContent = {
                            Text(it)
                        },
                        supportingContent = { Text("Swipe me left or right!") }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun HomePreview() {
    PycoTheme {
        QuoteCard()
        //Home(listOf("Deine mom"))
    }
}