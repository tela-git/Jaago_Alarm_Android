package com.example.jaago.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val label: String,
    val isScheduled: Boolean,
    val timeInMillis: Long,
    val song: String,
    val doVibrate: Boolean
)
