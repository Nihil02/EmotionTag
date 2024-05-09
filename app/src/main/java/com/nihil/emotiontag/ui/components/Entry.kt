package com.nihil.emotiontag.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nihil.emotiontag.data.EntryData
import java.time.LocalDate

@Composable
fun Entry(entryData: EntryData) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.height(140.dp).fillMaxWidth(0.9f)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.weight(0.6f),
                text = entryData.date.toString(),
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
                overflow= TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                modifier = Modifier.weight(3f),
                text = entryData.text,
                textAlign = TextAlign.Center,
                overflow= TextOverflow.Ellipsis,
                maxLines = 3,
            )

        }
    }
}

@Preview
@Composable
fun Preview_Entry() {
    Entry(
        entryData = EntryData(
            "Titulo",
            "Textoksdmgdmffmvcxmvbdf,mbv,cxb,mcmb,dvcmbm,cx,mmbvc,xmb,mcx,bmdsxnlkasdvmds,morena,mdlkmvldsmzgñvls,dñmgvklzmvmzlkñdjfkskvmdmputostodosms,dfmmsafm,zxdcvmkzmkdanc,asd-,fz,lxmvcszmlkdfc",
            "",
            LocalDate.of(2024, 12, 31)
        )
    )
}