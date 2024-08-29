package com.example.jaago.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AlarmRepository {
    suspend fun setNewAlarm(alarm: AlarmEntity)

    fun getAllAlarms(): Flow<List<AlarmEntity>>
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
}