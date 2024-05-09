package com.nihil.emotiontag.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity
data class EntryData(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "text") val text: String = "",
    @ColumnInfo(name = "emotion") val emotion: Int = 0,
    @ColumnInfo(name = "date") val date: String = LocalDate.now().toString()
)
