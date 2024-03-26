package com.example.pyco.views

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.pyco.data.repositories.HabitsRepository
import com.example.pyco.worker.DailyHabitWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * All apps that use Hilt must contain an Application class that is annotated with @HiltAndroidApp.
 *
 * @HiltAndroidApp triggers Hilt's code generation, including a base class for your application that serves as the application-level dependency container.
 * This generated Hilt component is attached to the Application object's lifecycle and provides dependencies to it.
 * Additionally, it is the parent component of the app, which means that other components can access the dependencies that it provides.
 */
@HiltAndroidApp
class PycoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val dailyHabitWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<DailyHabitWorker>(
                1,
                TimeUnit.DAYS
            ).build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                "DailyHabitWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyHabitWorkRequest
            )
    }
}