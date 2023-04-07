package com.jpndev.patients.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jpndev.patients.data.model.Patient


@Database(entities = [Patient::class],
version = 1,
exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun appDao(): DAO
}