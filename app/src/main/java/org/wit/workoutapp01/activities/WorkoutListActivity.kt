package org.wit.workoutapp01.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_workout_list.*
import org.jetbrains.anko.intentFor
import org.wit.workoutapp01.R
import org.wit.workoutapp01.main.MainApp
import org.jetbrains.anko.startActivityForResult
import org.wit.workoutapp01.models.WorkoutModel

class WorkoutListActivity : AppCompatActivity(), WorkoutListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)
        app = application as MainApp

        //layout and populate for display
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager   //recyclerView is a widget in activity_workout_list.xml
        loadWorkouts()

        //enable action bar and set title
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<WorkoutActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWorkoutClick(workout: WorkoutModel) {
        startActivityForResult(intentFor<WorkoutActivity>().putExtra("workout_edit", workout), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadWorkouts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadWorkouts() {
        showWorkouts(app.workouts.findAll())
    }

    fun showWorkouts (workouts: List<WorkoutModel>) {
        recyclerView.adapter = WorkoutAdapter(workouts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

