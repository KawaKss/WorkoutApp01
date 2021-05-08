package org.wit.workoutapp01.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.workout.helpers.*
import java.util.*

val JSON_FILE = "workouts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<WorkoutModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class WorkoutJSONStore : WorkoutStore, AnkoLogger {

    val context: Context
    var workouts = mutableListOf<WorkoutModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<WorkoutModel> {
        return workouts
    }

    override fun create(workout: WorkoutModel) {
        workout.id = generateRandomId()
        workouts.add(workout)
        serialize()
    }

    override fun update(workout: WorkoutModel) {
        val workoutsList = findAll() as ArrayList<WorkoutModel>
        var foundWorkout: WorkoutModel? = workoutsList.find { p -> p.id == workout.id }
        if (foundWorkout != null) {
            foundWorkout.title = workout.title
            foundWorkout.description = workout.description
            foundWorkout.link = workout.link
            foundWorkout.image = workout.image
            foundWorkout.lat = workout.lat
            foundWorkout.lng = workout.lng
            foundWorkout.zoom = workout.zoom
        }
        serialize()
    }

    override fun delete(workout: WorkoutModel) {
        workouts.remove(workout)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(workouts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        workouts = Gson().fromJson(jsonString, listType)
    }
}