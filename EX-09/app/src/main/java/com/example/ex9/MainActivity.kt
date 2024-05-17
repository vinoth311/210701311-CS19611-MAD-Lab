package com.example.ex9

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var alarmTimePicker: TimePicker
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmTimePicker = findViewById(R.id.timePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
    }

    // onToggleClicked() method is implemented for the time functionality
    fun onToggleClicked(view: View) {
        var time: Long
        if ((view as ToggleButton).isChecked) {
            Toast.makeText(this@MainActivity, "ALARM ON", Toast.LENGTH_SHORT).show()
            val calendar = Calendar.getInstance()

            // calendar is called to get the current time in hour and minute
            calendar[Calendar.HOUR_OF_DAY] = alarmTimePicker.currentHour
            calendar[Calendar.MINUTE] = alarmTimePicker.currentMinute

            // using intent I have class AlarmReceiver class which inherits
            // BroadcastReceiver
            val intent = Intent(this, AlarmReceiver::class.java)

            // we call broadcast using pendingIntent
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

            time = calendar.timeInMillis - calendar.timeInMillis % 60000
            if (System.currentTimeMillis() > time) {
                // setting time as AM and PM
                if (calendar[Calendar.AM_PM] == 0) time += 1000 * 60 * 60 * 12.toLong()
                else time += 1000 * 60 * 60 * 24.toLong()
            }
            // Alarm rings continuously until the toggle button is turned off
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent)
            // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this@MainActivity, "ALARM OFF", Toast.LENGTH_SHORT).show()
        }
    }
}
