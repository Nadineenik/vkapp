package com.nadinee.vkapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.nadinee.vkapp.ui.FirstScreen
import com.nadinee.vkapp.ui.theme.VkappTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            VkappTheme {
//                Surface(color = MaterialTheme.colorScheme.background) {
//                    FirstScreen(
//                        onOpenSecond = { text ->
//                            val intent = Intent(this, SecondActivity::class.java).apply {
//                                putExtra(SecondActivity.EXTRA_TEXT, text)
//                            }
//                            startActivity(intent)
//                        }
//                    )
//                }
//            }
//        }
        setContent {
            VkappTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}
