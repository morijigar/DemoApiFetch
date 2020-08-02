package com.example.myapplication.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apifetch.databse.PostDao
import com.example.apifetch.databse.Rows

@Database(entities = [Rows::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun PostDao(): PostDao

}


