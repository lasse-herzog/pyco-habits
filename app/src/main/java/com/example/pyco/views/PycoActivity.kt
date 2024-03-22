package com.example.pyco.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pyco.views.ui.theme.PycoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is the Main activity for the Pyco app.
 */
@AndroidEntryPoint
class PycoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            PycoTheme {
                PycoNavigationBar()
            }
        }
    }
}
