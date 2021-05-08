package org.wit.workoutapp01.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_workout.view.*
import kotlinx.android.synthetic.main.card_workout.view.*
import kotlinx.android.synthetic.main.card_workout.view.workoutTitle
import org.wit.workout.R
import org.wit.workoutapp01.helpers.readImageFromPath
import org.wit.workoutapp01.models.WorkoutModel

interface WorkoutListener {
    fun onWorkoutClick(workout: WorkoutModel)
}

class WorkoutAdapter constructor(private var workouts: List<WorkoutModel>,
                                   private val listener: WorkoutListener) : RecyclerView.Adapter<WorkoutAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_workout, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val workout = workouts[holder.adapterPosition]
        holder.bind(workout, listener)
    }

    override fun getItemCount(): Int = workouts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(workout: WorkoutModel,  listener : WorkoutListener) {
            itemView.workoutTitle.text = workout.title
            itemView.description.text = workout.description
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, workout.image))
            itemView.setOnClickListener { listener.onWorkoutClick(workout) }
        }
    }
}