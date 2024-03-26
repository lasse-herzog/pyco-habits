package com.example.pyco.views

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pyco.data.repositories.HabitsRepository
import com.example.pyco.views.ui.theme.PycoTheme
import com.example.pyco.worker.HabitWorker
import dagger.Module
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * This is the Main activity for the Pyco app.
 */
@AndroidEntryPoint
class PycoActivity :
    ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            PycoTheme {
                PycoNavigationBar()
            }
        }
    }

    override fun onStart() {
        runHabitWorker(this.applicationContext)
        super.onStart()
    }

    override fun onResume() {
        runHabitWorker(this.applicationContext)
        super.onResume()
    }

    private fun runHabitWorker(applicationContext: Context) {
        runBlocking {
            HabitWorker(applicationContext).doWork()
        }
    }
}