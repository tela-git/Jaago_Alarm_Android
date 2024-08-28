package com.example.jaago.domain.model

import androidx.compose.runtime.MutableState

data class Alarm(
    var time: String = "12:00",
    var label: MutableState<String>,
    var repetition: Days,
    var doVibrate: Boolean = false,
    var Song: String = "Default"
)
