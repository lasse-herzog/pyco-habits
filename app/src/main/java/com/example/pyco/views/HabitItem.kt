package com.example.pyco.views

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.R
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.viewmodels.HabitsOverviewViewModel
import com.example.pyco.views.ui.theme.PycoTheme
import java.time.LocalDate

object CategoryIcons{
    val iconDictionary = hashMapOf(
        1 to R.mipmap.ic_cat_sport_icon,
        2 to R.mipmap.ic_cat_persdev_icon,
        3 to R.mipmap.ic_cat_social_icon,
        4 to R.mipmap.ic_cat_finance_icon,
        5 to R.mipmap.ic_cat_career_icon,
        6 to R.mipmap.ic_cat_job_icon,
        7 to R.mipmap.ic_cat_freetime_icon,
        8 to R.mipmap.ic_cat_habit_icon,
        9 to R.mipmap.ic_cat_env_icon,
        10 to R.mipmap.ic_cat_love_icon
    )
}
@Composable
fun HabitItem(habit: HabitAndHabitBlueprintWithCategories, viewModel: HabitsOverviewViewModel) {
    val context = LocalContext.current
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val openDeleteDialog = remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        color =  MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier
            .animateContentSize()
            .padding(5.dp)
            .clickable { /* TODO: open the details view */ }
    ){
        Row(modifier = Modifier
            .padding(all = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(CategoryIcons.iconDictionary.getOrDefault(habit.categories.first().categoryId, R.mipmap.ic_habit_icon)),
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
                    .width(245.dp)
            ) {
                Text(
                    text = habit.habitAndHabitBlueprint.habitBlueprint.name,
                    maxLines = 1,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal=5.dp)
                )
            }
            /*
            Text(
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
                    .weight(1f),
                text = habit.habitAndHabitBlueprint.habit.interval.toString() + " Tage"
            )
            */

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
                                contentDescription = "Delete Habit"
                            )
                        },
                        onClick = {
                            openDeleteDialog.value = true
                        }
                    )
                }
            }
            when{
                openDeleteDialog.value ->{
                    DeleteDialog(
                        onDismissRequest = { openDeleteDialog.value = false },
                        onConfirmation = {
                            viewModel.remove(habit.habitAndHabitBlueprint.habitBlueprint)
                            openDeleteDialog.value = false
                            Toast.makeText(context, "Habit \"" + habit.habitAndHabitBlueprint.habitBlueprint.name + "\"  deleted", Toast.LENGTH_SHORT).show()
                        },
                        dialogTitle = "Delete Habit",
                        dialogText = "Are you sure you want to delete the habit \"" + habit.habitAndHabitBlueprint.habitBlueprint.name + "\"?",
                        icon = Icons.Default.Warning
                    )
                }
            }
        }
    }
}
@Composable
fun DeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Alert Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Back")
            }
        }
    )
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewHabitsItem() {
    val viewModel = hiltViewModel<HabitsOverviewViewModel>()
    PycoTheme {
        Surface {
            HabitItem(
                habit = HabitAndHabitBlueprintWithCategories(
                    HabitAndHabitBlueprint(
                        Habit(0, 0, LocalDate.now(), LocalDate.now().plusDays(5), 1),
                        HabitBlueprint(0, "Müll rausbringen", "bitte ich will nicht mehr")
                    ),
                    mutableListOf(Category(0, "Saufen"), Category(1, "Achtarmig reinorgeln"))
                ), viewModel
            )
        }
    }
}