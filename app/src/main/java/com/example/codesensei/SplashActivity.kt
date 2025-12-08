package com.example.codesensei

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Simple splash screen activity.
 *
 * - Shows a black background with the Code Sensei logo centered.
 * - Waits ~1 second.
 * - Then opens [MainActivity] and finishes itself.
 */
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show the logo using Compose
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_codesensei_logo),
                    // contentDescription is important for accessibility services (e.g., TalkBack)
                    contentDescription = "Code Sensei Logo"
                )
            }
        }

        // After a small delay, go to MainActivity
        // We use lifecycleScope to launch a coroutine that is automatically cancelled
        // when the Activity is destroyed, preventing potential memory leaks.
        lifecycleScope.launch {
            // The delay simulates loading time and ensures the splash screen is visible
            // for a short period, improving user experience.
            delay(1500L)
            // Navigate to the main screen of the application.
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}