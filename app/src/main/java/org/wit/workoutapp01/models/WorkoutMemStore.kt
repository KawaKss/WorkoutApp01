package org.wit.workoutapp01.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class WorkoutMemStore : WorkoutStore, AnkoLogger {

    val workouts = ArrayList<WorkoutModel>()

    override fun findAll(): List<WorkoutModel> {
        return workouts
    }

    override fun create(workout: WorkoutModel) {
        workout.id = getId()
        workouts.add(workout)
        logAll()
    }

    override fun update(workout: WorkoutModel) {
        var foundWorkout: WorkoutModel? = workouts.find { p -> p.id == workout.id }
        if (foundWorkout != null) {
            foundWorkout.title = workout.title
            foundWorkout.description = workout.description
            foundWorkout.link = workout.link
            foundWorkout.image = workout.image
            foundWorkout.lat = workout.lat
            foundWorkout.lng = workout.lng
            foundWorkout.zoom = workout.zoom
            logAll();
        }
    }

    override fun delete(workout: WorkoutModel) {
        workouts.remove(workout)
    }

    fun logAll() {
        workouts.forEach { info("${it}") }
    }
}