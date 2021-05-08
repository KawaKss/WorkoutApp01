package org.wit.workoutapp01.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_workout.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.workout.R
import org.wit.workoutapp01.helpers.readImage
import org.wit.workoutapp01.helpers.readImageFromPath
import org.wit.workoutapp01.helpers.showImagePicker
import org.wit.workoutapp01.main.MainApp
import org.wit.workoutapp01.models.Location
import org.wit.workoutapp01.models.WorkoutModel

class WorkoutActivity : AppCompatActivity(), AnkoLogger {

    var workout = WorkoutModel()
    lateinit var app : MainApp
    var edit = false
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        app = application as MainApp

        if (intent.hasExtra("workout_edit")) {
            edit = true
            workout = intent.extras?.getParcelable<WorkoutModel>("workout_edit")!!
            workoutTitle.setText(workout.title)
            workoutDescription.setText(workout.description)
            workoutLink.setText(workout.link)
            workoutImage.setImageBitmap(readImageFromPath(this, workout.image))
            if (workout.image != null) {
                chooseImage.setText(R.string.change_workout_image)
            }
            btnAdd.setText(R.string.save_workout)
        }

        workoutLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (workout.zoom != 0f) {
                location.lat =  workout.lat
                location.lng = workout.lng
                location.zoom = workout.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        btnAdd.setOnClickListener() {
            workout.title = workoutTitle.text.toString()
            workout.description = workoutDescription.text.toString()
            workout.link = workoutLink.text.toString()
            if (workout.title.isEmpty()) {
                toast(R.string.enter_workout_title)
            } else {
                if (edit) {
                    app.workouts.update(workout.copy())
                } else {
                    app.workouts.create(workout.copy())
                }
            }
            info("add Button Pressed: $workoutTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_workout, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.workouts.delete(workout)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    workout.image = data.getData().toString()
                    workoutImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_workout_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    workout.lat = location.lat
                    workout.lng = location.lng
                    workout.zoom = location.zoom
                }
            }
        }
    }
}