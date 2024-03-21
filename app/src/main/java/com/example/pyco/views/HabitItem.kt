package com.example.pyco.views

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pyco.R
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.views.ui.theme.PycoTheme
import java.time.LocalDate

@Composable
fun HabitItem(habit: HabitAndHabitBlueprint) {
    val context = LocalContext.current
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    var isBadHabit = habit.habitBlueprint.badHabit

    val detailsColor by animateColorAsState(
        if (isBadHabit) MaterialTheme.colorScheme.inverseOnSurface  else MaterialTheme.colorScheme.surfaceContainerLow,
    )

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        color = detailsColor,
        modifier = Modifier
            .animateContentSize()
            .padding(5.dp)
            .clickable { /* TODO: open the details view */ }
    ){
        Row(modifier = Modifier
            .padding(all = 9.dp)
        ){
            Image(
                painter = painterResource(R.mipmap.ic_habit_icon),
                contentDescription = "Placeholder icon",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .shadow(
                        elevation = 1.dp,
                        shape = CircleShape,
                        ambientColor = MaterialTheme.colorScheme.surface
                    )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .width(190.dp)
            ) {
                Text(
                    text = habit.habitBlueprint.name,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = habit.habitBlueprint.description,
                    modifier = Modifier.padding(vertical = 3.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

            }

            Text(
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
                    .weight(1f),
                text = habit.habit.interval.toString() + " Tage"
            )
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopEnd)

            ) {
                IconButton(
                    onClick = { showDropdown = !showDropdown },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                    //.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Localized description"
                    )
                }

                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = { showDropdown = !showDropdown }) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Localized description"
                            )
                        },
                        onClick = { Toast.makeText(context, "Load", Toast.LENGTH_SHORT).show() }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Localized description"
                            )
                        },
                        onClick = { Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show() }
                    )
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
fun PreviewHabitsItem() {
    PycoTheme {
        Surface {
            HabitItem(
                habit = HabitAndHabitBlueprint(
                    Habit(0, 0, LocalDate.now(), LocalDate.now().plusDays(5), 1),
                    HabitBlueprint(0, "Müll rausbringen", "bitte ich will nicht mehr")
                )
            )
        }
    }
}