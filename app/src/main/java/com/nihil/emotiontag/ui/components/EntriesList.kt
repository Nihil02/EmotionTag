package com.nihil.emotiontag.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.EntryData

@Composable
fun EntriesList(entries: List<EntryData>? = null) {
    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
    if (entries == null) {
        Text(
            modifier = Modifier.fillMaxHeight(0.8f).fillMaxWidth(0.8f),
            text = stringResource(R.string.lblEntriesNoList),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
        )
    } else {
        LazyColumn {
            items(entries) { entry ->
                Entry(entry)
                Spacer(modifier = Modifier.size(width = 1.dp, height = 20.dp))
            }
        }
    }
    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
}
@Preview
@Composable
fun Preview_List() {
    val datos = listOf(
        EntryData("Titulo", "Texto", ""),
        EntryData("Titulo2", "Texto", "")
    )

    EntriesList(entries = datos)
}
