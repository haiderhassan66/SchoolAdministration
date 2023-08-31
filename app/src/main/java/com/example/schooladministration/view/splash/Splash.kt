package com.example.schooladministration.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.schooladministration.R
import com.example.schooladministration.databinding.ActivitySplashBinding
import com.example.schooladministration.view.mainActivity.MainActivity
import com.example.schooladministration.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Splash : AppCompatActivity() {
    lateinit var binding:ActivitySplashBinding
    private val viewModel:SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        binding.viewModel = viewModel
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@Splash,MainActivity::class.java))
            finish()
        }
    }
}