package com.nihil.emotiontag.ui.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.database.entities.EntryData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Entry(entryData: EntryData, navController: NavController) {
    val context = LocalContext.current
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = { navController.navigate(ScreenData.ShowEntryScreen.title + "/" + entryData.id.toString() + "") },
                onLongClick = {
                    Toast
                        .makeText(
                            context,
                            "TODO for edit and delete",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = entryData.date,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraLight,
                maxLines = 1,
            )
            Text(
                modifier = Modifier.weight(2f),
                text = entryData.title,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                modifier = Modifier.weight(3f),
                text = entryData.text,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
            )

        }
    }
}

@Preview
@Composable
fun Preview_Entry() {
}