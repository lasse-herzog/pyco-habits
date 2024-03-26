package com.example.pyco.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pyco.data.repositories.HabitsRepository
import com.example.pyco.views.ui.theme.PycoTheme
import dagger.Module
import dagger.hilt.android.AndroidEntryPoint
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
        super.onStart()
    }
}