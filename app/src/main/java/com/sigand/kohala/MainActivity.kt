package com.sigand.kohala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sigand.kohala.ui.KohalaApp
import com.sigand.kohala.ui.theme.KohalaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KohalaTheme {
                KohalaApp()
            }
        }
    }
}
