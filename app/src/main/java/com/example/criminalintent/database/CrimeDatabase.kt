package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.criminalintent.Crime

@Database(entities = [ Crime::class ], version = 1, exportSchema = false)
@TypeConverters(CrimeTypeConverters::class)                                                                  // Явное добавление конвертеров к классу базы данных.
abstract class CrimeDatabase : RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}