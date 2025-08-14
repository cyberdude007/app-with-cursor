package com.paisasplit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import dagger.hilt.android.AndroidEntryPoint
import com.paisasplit.app.navigation.AppNavHost
import com.paisasplit.app.ui.theme.PaisaSplitTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaisaSplitTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PaisaSplitApp()
                }
            }
        }
    }
}

@Composable
fun PaisaSplitApp() {
    AppNavHost()
}


