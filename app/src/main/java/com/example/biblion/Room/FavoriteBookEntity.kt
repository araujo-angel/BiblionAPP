package com.example.biblion.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBookEntity(
    @PrimaryKey val bookId: String,
    val title: String,
    val pages: String,
    val imagePath: String,
    val price: Double,
    val addedDate: Long = System.currentTimeMillis(),
)