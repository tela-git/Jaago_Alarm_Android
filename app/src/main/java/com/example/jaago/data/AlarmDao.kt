package com.example.jaago.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AlarmDao {
    @Insert
    abstract suspend fun addNewAlarm(alarm: AlarmEntity)

    @Query("SELECT * FROM alarms")
    abstract fun getAllAlarms() : Flow<List<AlarmEntity>>
}