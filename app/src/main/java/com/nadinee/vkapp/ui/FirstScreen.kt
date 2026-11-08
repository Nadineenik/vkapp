package com.nadinee.vkapp.ui


import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nadinee.vkapp.R

@Composable
fun FirstScreen(
    onOpenSecond: (String) -> Unit
) {
    val context = LocalContext.current

    var inputText by remember { mutableStateOf("") }

    val emptyError = stringResource(R.string.error_empty)
    val invalidPhoneError = stringResource(R.string.error_invalid_phone)
    val noDialerError = stringResource(R.string.error_no_dialer)
    val chooserTitle = stringResource(R.string.chooser_title)

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.hint_input)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true
        )

        // ---------- 1. ЯВНЫЙ ИНТЕНТ: переход во вторую Activity ----------
        Button(
            onClick = {
                val text = inputText.trim()
                if (text.isEmpty()) {
                    showToast(emptyError)
                } else {
                    onOpenSecond(text)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_open_second))
        }

        // ---------- 2. НЕЯВНЫЙ ИНТЕНТ: ACTION_DIAL ----------
        Button(
            onClick = {
                val phone = inputText.trim()
                when {
                    phone.isEmpty() -> showToast(emptyError)

                    !Regex("^[+]?[0-9\\s\\-()]{3,}$").matches(phone) ->
                        showToast(invalidPhoneError)

                    else -> {
                        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${Uri.encode(phone)}")
                        }
                        try {
                            context.startActivity(dialIntent)
                        } catch (e: Exception) {
                            showToast(noDialerError)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_call_friend))
        }

        // ---------- 3. СИСТЕМНЫЙ ИНТЕНТ: ACTION_SEND ----------
        Button(
            onClick = {
                val text = inputText.trim()
                if (text.isEmpty()) {
                    showToast(emptyError)
                } else {
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    val chooser = Intent.createChooser(sendIntent, chooserTitle)
                    context.startActivity(chooser)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_share_text))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstScreenPreview() {
    FirstScreen(onOpenSecond = {})
}