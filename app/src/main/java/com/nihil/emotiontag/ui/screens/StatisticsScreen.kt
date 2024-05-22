package com.nihil.emotiontag.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.nihil.emotiontag.R
import com.nihil.emotiontag.ui.components.BottomBar
import com.nihil.emotiontag.ui.components.TopBar
import com.nihil.emotiontag.util.LocalEntryViewModel
import com.nihil.emotiontag.util.getEmotionColor
import com.nihil.emotiontag.util.getEmotionText

/**
 * App Screen
 *
 * Screen for visualizing the statistics from last week
 **/
@Composable
fun StatisticsScreen() {
    val lastWeekEmotions by LocalEntryViewModel.current.getLastWeekEmotions().observeAsState(
        emptyList()
    )

    Log.i("Emotions", lastWeekEmotions.toString())

    Scaffold(
        topBar = { TopBar(title = stringResource(R.string.scrTiitleStatistics)) },
        bottomBar = { BottomBar() },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (lastWeekEmotions.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.lblEntriesNoList),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            } else {
                val emotionsCount = lastWeekEmotions.groupingBy { it }.eachCount()
                val pieChartData = emotionsCount.map { (emotion, count) ->
                    PieChartData.Slice(
                        value = count.toFloat(),
                        color = getEmotionColor(emotion)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(id = R.string.lblStatisticsWeek),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        PieChart(
                            pieChartData = PieChartData(pieChartData),
                        )
                    }

                    emotionsCount.keys.forEachIndexed { index, emotion ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_entries),
                                contentDescription = "",
                                tint = getEmotionColor(emotion)
                            )
                            Text(text = getEmotionText(emotion))
                        }
                    }
                }
            }
        }
    }
}
