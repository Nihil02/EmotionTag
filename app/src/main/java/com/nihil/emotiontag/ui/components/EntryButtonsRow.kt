package com.nihil.emotiontag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nihil.emotiontag.R

@Composable
fun EntryButtonsRow(
    onActionButtonTitle: String = "",
    isReady: Boolean,
    isProcessing: Boolean,
    onActionClick: () -> Unit = {},
    onAnalyzeClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        if (onActionButtonTitle.isNotBlank()) {
            Button(onClick = onActionClick, enabled = isReady && !isProcessing) {
                Text(onActionButtonTitle)
            }
        }
        Button(onClick = onAnalyzeClick, enabled = !isProcessing) {
            Text(stringResource(R.string.btnTextAnalyze))
        }
    }
}