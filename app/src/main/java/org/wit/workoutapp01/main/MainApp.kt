package org.wit.workoutapp01.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.workoutapp01.models.WorkoutJSONStore
import org.wit.workoutapp01.models.WorkoutStore

class MainApp : Application(), AnkoLogger {

    lateinit var workouts: WorkoutStore

    override fun onCreate() {
        super.onCreate()
        workouts = WorkoutJSONStore(applicationContext)
        info("Workout started")
    }
}