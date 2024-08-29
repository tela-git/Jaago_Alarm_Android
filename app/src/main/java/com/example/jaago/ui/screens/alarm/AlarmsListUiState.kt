package com.example.jaago.ui.screens.alarm

import com.example.jaago.data.Alarm

data class AlarmsListUiState(
    val alarms: List<Alarm> = emptyList(),
    val isError: Boolean = true
)
