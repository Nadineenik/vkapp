package com.nadinee.vkapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.nadinee.vkapp.ui.SecondScreen
import com.nadinee.vkapp.ui.theme.VkappTheme

class SecondActivity : ComponentActivity() {

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedText: String = intent.getStringExtra(EXTRA_TEXT).orEmpty()

        setContent {
            VkappTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SecondScreen(
                        text = receivedText,
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}
