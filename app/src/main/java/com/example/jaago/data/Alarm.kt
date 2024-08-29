package com.example.jaago.data

data class Alarm(
    val label: String,
    val isScheduled: Boolean,
    val timeInMillis: Long,
    val song: String,
    val doVibrate: Boolean
)
