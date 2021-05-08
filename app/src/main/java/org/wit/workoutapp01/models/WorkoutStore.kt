package org.wit.workoutapp01.models

interface WorkoutStore {
    fun findAll(): List<WorkoutModel>
    fun create(workout: WorkoutModel)
    fun update(workout: WorkoutModel)
    fun delete(workout: WorkoutModel)
}