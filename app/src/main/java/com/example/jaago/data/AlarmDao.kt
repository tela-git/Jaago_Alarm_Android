package com.example.jaago.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addNewAlarm(alarm: AlarmEntity)

    @Query("SELECT * FROM alarms")
    abstract fun getAllAlarms() : Flow<List<AlarmEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateAlarm(alarm: AlarmEntity)

    @Delete
    abstract suspend fun deleteAlarm(alarm: AlarmEntity)
}