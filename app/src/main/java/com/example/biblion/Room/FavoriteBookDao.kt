package com.example.biblion.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: FavoriteBookEntity)

    @Delete
    suspend fun delete(book: FavoriteBookEntity)

    @Query("SELECT * FROM favorite_books ORDER BY addedDate DESC")
    fun getAll(): Flow<List<FavoriteBookEntity>>

    @Query("SELECT * FROM favorite_books WHERE bookId = :bookId")
    suspend fun getById(bookId: String): FavoriteBookEntity?
}