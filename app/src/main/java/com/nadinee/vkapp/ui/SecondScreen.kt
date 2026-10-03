package com.nadinee.vkapp.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadinee.vkapp.R

@Composable
fun SecondScreen(
    text: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = text.ifEmpty { stringResource(R.string.no_text) },
            fontSize = 20.sp
        )

        Button(onClick = onBack) {
            Text(stringResource(R.string.btn_back))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondScreenPreview() {
    SecondScreen(text = "Пример текста", onBack = {})
}
