package com.example.jaago.data

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object  HiltModule {

    @Provides
    @Singleton
    fun provideAlarmDatabase(app: Application): AlarmDatabase {
        var INSTANCE: AlarmDatabase? = null
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context = app,
                name = "AlarmDatabase",
                klass = AlarmDatabase::class.java
            )
                .build()
            INSTANCE = instance
            instance
        }
    }

    @Provides
    fun provideAlarmRepository(
        database: AlarmDatabase
    ): AlarmRepository {
        return AlarmRepositoryImpl(
            database.getAlarmDao()
        )
    }
}