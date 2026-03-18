package com.roox.medboard.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.roox.medboard.databinding.ActivitySplashBinding
import com.roox.medboard.ui.main.MainActivity
import com.roox.medboard.viewmodel.MainViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoaded.observe(this) { loaded ->
            if (loaded) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        viewModel.loadContent()
    }
}
