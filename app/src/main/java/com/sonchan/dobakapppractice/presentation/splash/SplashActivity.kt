package com.sonchan.dobakapppractice.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sonchan.dobakapppractice.R
import com.sonchan.dobakapppractice.presentation.main.MainActivity
import com.sonchan.dobakapppractice.presentation.splash.ui.theme.DobakAppPracticeTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DobakAppPracticeTheme {

                SplashScreen()
            }
        }
    }

    @Preview
    @Composable
    fun SplashScreen(

    ) {
        val alpha = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = Unit) {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(1500)
            )
            delay(2000L)

            Intent(this@SplashActivity, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.let { intent ->
                startActivity(intent)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val painter = painterResource(R.drawable.logo)

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(93.dp)
                        .alpha(alpha.value),
                    painter = painter,
                    contentDescription = "Welluga Staff",
                )
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha.value),
                painter = painterResource(id = R.drawable.logo),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Welluga Staff"
            )
        }
    }
}