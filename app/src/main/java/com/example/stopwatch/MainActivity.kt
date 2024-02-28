package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {

    private lateinit var tvTime: TextView
    private lateinit var btnStartPause: Button
    private lateinit var btnReset: Button

    private var isRunning = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0

    private val handler = android.os.Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTime = findViewById(R.id.tvTime)
        btnStartPause = findViewById(R.id.btnStartPause)
        btnReset = findViewById(R.id.btnReset)
    }

    fun startPauseClick(view: View) {
        if (isRunning) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    fun resetClick(view: View) {
        resetTimer()
    }

    private fun startTimer() {
        isRunning = true
        btnStartPause.text = "Pause"
        btnReset.isEnabled = false

        startTime = SystemClock.elapsedRealtime() - elapsedTime

        handler.postDelayed(object : Runnable {
            override fun run() {
                val currentTime = SystemClock.elapsedRealtime()
                elapsedTime = currentTime - startTime
                updateTimer(elapsedTime)
                handler.postDelayed(this, 10) // Update every 10 milliseconds
            }
        }, 10)
    }

    private fun pauseTimer() {
        isRunning = false
        btnStartPause.text = "Start"
        btnReset.isEnabled = true

        handler.removeCallbacksAndMessages(null)
    }

    private fun resetTimer() {
        isRunning = false
        btnStartPause.text = "Start"
        btnReset.isEnabled = false

        elapsedTime = 0
        updateTimer(elapsedTime)
    }

    private fun updateTimer(elapsedTime: Long) {
        val minutes = (elapsedTime / 60000).toString().padStart(2, '0')
        val seconds = ((elapsedTime % 60000) / 1000).toString().padStart(2, '0')
        val milliseconds = (elapsedTime % 1000).toString().padStart(3, '0')

        val timeString = "$minutes:$seconds:$milliseconds"
        tvTime.text = timeString
    }
}