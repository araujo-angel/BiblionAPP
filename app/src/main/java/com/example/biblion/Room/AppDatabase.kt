package com.example.biblion.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// In AppDatabase:
@Database(entities = [FavoriteBookEntity::class], version = 1, exportSchema = false )
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteBookDao(): FavoriteBookDao
}