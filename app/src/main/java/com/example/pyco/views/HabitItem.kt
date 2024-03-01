package com.example.pyco.views

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pyco.R
import com.example.pyco.views.ui.theme.PycoTheme

data class Habit(val title: String, val intervall: String)

@Composable
fun HabitItem(habit: Habit){
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val detailsColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        color = detailsColor,
        modifier = Modifier
            .animateContentSize()
            .padding(4.dp)
    ){
        Row(modifier = Modifier
            .padding(all = 8.dp)
        ){
            Image(
                painter = painterResource(R.mipmap.ic_habit_icon),
                contentDescription = "Placeholder icon",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .shadow(elevation = 1.dp, shape = CircleShape, ambientColor = MaterialTheme.colorScheme.surface)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .width(250.dp)
            ) {
                Text(
                    text = habit.title,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = habit.intervall,
                    modifier = Modifier.padding(vertical = 3.dp),
                    //fontSize = 10.sp
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp).weight(1f),
                text = "4 Tage"
            )

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
fun PreviewHabitsItem(){
    PycoTheme {
        Surface {
            HabitItem(habit = Habit("Müll rausbringen", "bitte ich will nicht mehr"))
        }
    }
}