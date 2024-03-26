package com.example.oibsip_task05

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.oibsip_task05.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var flag = true
    private var currentSecond = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            // Update UI or perform any action needed for stopwatch
            currentSecond++
            // Example: Log the current second
            val hour = currentSecond / 3600
            val min = (currentSecond % 3600) / 60
            val sec = currentSecond % 60
            val time = String.format("%02d:%02d:%02d", hour, min, sec)
            //println("Current Time: $time")

            // Set the formatted time to the appropriate UI element
            binding.Time.text = time

            // Repeat the task after 1 second
            handler.postDelayed(this, 1000)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge handling
       enableEdgeToEdge()

        // Inflate the layout using view binding
        setContentView(binding.root)

        // Apply insets to the root view
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set click listeners for buttons
        binding.start.setOnClickListener {
            startStopwatch()
        }
        binding.pause.setOnClickListener {
            pauseStopwatch()
        }
        binding.reset.setOnClickListener {
            resetStopwatch()
        }
    }

    private fun startStopwatch() {
        // Start the stopwatch when the start button is clicked
        if(flag){
            handler.post(runnable)
            flag = false
            binding.start.isEnabled = false
            binding.pause.isEnabled = true
            binding.reset.isEnabled = true
        }
    }

    private fun pauseStopwatch() {
        // Stop the stopwatch when the pause button is clicked
        handler.removeCallbacks(runnable)
        flag = true
        binding.start.isEnabled = true
        binding.pause.isEnabled = false
        binding.reset.isEnabled = true
    }

    private fun resetStopwatch() {
        // Reset the stopwatch when the reset button is clicked
        currentSecond = 0
        binding.Time.text = "00:00:00"
        flag = true
        binding.start.isEnabled = true
        binding.pause.isEnabled = false
        binding.reset.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the stopwatch when the activity is destroyed
        handler.removeCallbacks(runnable)
    }
}
