package com.example.jaago.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AlarmRepository {
    suspend fun setNewAlarm(alarm: AlarmEntity)

    fun getAllAlarms(): Flow<List<AlarmEntity>>

    suspend fun updateAlarm(alarm: AlarmEntity)

    suspend fun deleteAlarm(alarm: AlarmEntity)
}

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
): AlarmRepository {

    override suspend fun setNewAlarm(alarm: AlarmEntity) {
        alarmDao.addNewAlarm(alarm)
    }

    override fun getAllAlarms(): Flow<List<AlarmEntity>> {
        return alarmDao.getAllAlarms()
    }

    override suspend fun updateAlarm(alarm: AlarmEntity) {
        alarmDao.updateAlarm(alarm = alarm)
    }

    override suspend fun deleteAlarm(alarm: AlarmEntity) {
        alarmDao.deleteAlarm(alarm)
    }
}