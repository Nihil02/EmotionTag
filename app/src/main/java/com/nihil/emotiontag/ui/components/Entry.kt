package com.nihil.emotiontag.ui.components

import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nihil.emotiontag.EmotionTagApplication
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.database.vm.EntryViewModel
import com.nihil.emotiontag.database.vm.EntryViewModelFactory
import com.nihil.emotiontag.util.deleteFromDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Entry(entryData: EntryData, navController: NavController, entryViewModel: EntryViewModel) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
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
                    showBottomSheet = true
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

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(36.dp)
                    ) {
                        TextButton(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                    navController.navigate(ScreenData.UpdateEntryScreen.title + "/" + entryData.id.toString() + "")
                                }
                            }
                        }) {
                            Text(stringResource(id = R.string.btnModalEdit))
                        }

                        TextButton(onClick = {
                            scope.launch { sheetState.hide()
                            }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                                deleteFromDatabase(entryViewModel, entryData, context)
                            }
                        }) {
                            Text(stringResource(id = R.string.btnModalDelete))
                        }
                    }

                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}
