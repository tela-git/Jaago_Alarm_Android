package com.example.jaago.ui.screens.alarm

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jaago.AlarmHelper
import com.example.jaago.data.AlarmRepository
import com.example.jaago.data.Alarm
import com.example.jaago.data.AlarmEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    @ApplicationContext val context: Context
): ViewModel() {

    val alarmHelper by lazy {
        AlarmHelper(context)
    }
    private val _alarmsListUiState: MutableStateFlow<AlarmsListUiState> = MutableStateFlow(AlarmsListUiState())
    val alarmsListUiState = _alarmsListUiState.asStateFlow()

    fun updateAlarmLabel(newLabel: String, alarm: Alarm) {
        viewModelScope.launch {
            alarmRepository.updateAlarm(
                alarm = AlarmEntity(
                    id = alarm.id,
                    isScheduled = alarm.isScheduled,
                    label = newLabel,
                    doVibrate = alarm.doVibrate,
                    song = alarm.song,
                    timeInMillis = alarm.timeInMillis
                )
            )
        }
    }

    fun updateAlarmScheduleState(newState: Boolean, alarm: Alarm) {
        viewModelScope.launch {
            alarmRepository.updateAlarm(
                alarm = AlarmEntity(
                    id = alarm.id,
                    isScheduled = newState,
                    label = alarm.label,
                    doVibrate = alarm.doVibrate,
                    song = alarm.song,
                    timeInMillis = alarm.timeInMillis
                )
            )
            if(newState) {
                alarmHelper.setAlarm(triggerTime = alarm.timeInMillis)
            }
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmRepository.deleteAlarm(
                alarm = AlarmEntity(
                    id = alarm.id,
                    isScheduled = alarm.isScheduled,
                    label = alarm.label,
                    doVibrate = alarm.doVibrate,
                    song = alarm.song,
                    timeInMillis = alarm.timeInMillis
                )
            )
        }
    }

    fun addNewAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmRepository.addNewAlarm(
                AlarmEntity(
                    doVibrate = alarm.doVibrate,
                    isScheduled = alarm.isScheduled,
                    label = alarm.label,
                    song = alarm.song,
                    timeInMillis = alarm.timeInMillis
                )
            )
        }
    }

    fun updateAlarmTime(newTime: Long, alarm: Alarm) {
        viewModelScope.launch {
            alarmRepository.updateAlarm(
                AlarmEntity(
                    doVibrate = alarm.doVibrate,
                    isScheduled = alarm.isScheduled,
                    label = alarm.label,
                    song = alarm.song,
                    timeInMillis = newTime
                )
            )
        }
    }

    init {
        viewModelScope.launch {
            try {
                alarmRepository.getAllAlarms()
                    .collect { listOfAlarmEntities ->
                        _alarmsListUiState.update { state ->
                            state.copy(
                                alarms = listOfAlarmEntities.map { alarmEntity ->
                                    Alarm(
                                        id = alarmEntity.id,
                                        doVibrate = alarmEntity.doVibrate,
                                        song = alarmEntity.song,
                                        isScheduled = alarmEntity.isScheduled,
                                        timeInMillis = alarmEntity.timeInMillis,
                                        label = alarmEntity.label
                                    )
                                },
                                isError = false
                            )
                        }
                    }
            } catch(e: Exception) {
                _alarmsListUiState.update { state->
                    state.copy(
                        isError = true
                    )
                }
            }
        }
    }
}