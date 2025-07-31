package com.example.biblion.Repository

import androidx.compose.runtime.mutableStateListOf
import com.example.biblion.Domain.BookModel

object BookRepository {
    var allBooks = mutableStateListOf<BookModel>()
}