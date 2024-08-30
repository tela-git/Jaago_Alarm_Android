package com.example.jaago.data

data class Alarm(
    val id: Int = 0,
    val label: String,
    val isScheduled: Boolean,
    val timeInMillis: Long,
    val song: String,
    val doVibrate: Boolean
)
