package com.nihil.emotiontag.ui.screens

import android.print.PrintAttributes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.nihil.emotiontag.R
import com.nihil.emotiontag.ui.components.BottomBar
import com.nihil.emotiontag.ui.components.TopBar
import com.nihil.emotiontag.util.LocalEntryViewModel

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
                val emotions = listOf("Neutral", "Happy", "Sad", "Angry", "Surprised")
                val colors = listOf(Color.Gray, Color.Yellow, Color.Blue, Color.Red, Color.Green)

                val pieChartData = lastWeekEmotions.mapIndexed { index, count ->
                    PieChartData.Slice(
                        value = count.toFloat(),
                        color = colors.getOrElse(index) { Color.Gray }
                    )
                }

                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ){
                        PieChart(
                            pieChartData = PieChartData(pieChartData),
                        )
                    }
                }
            }
        }
    }
}
