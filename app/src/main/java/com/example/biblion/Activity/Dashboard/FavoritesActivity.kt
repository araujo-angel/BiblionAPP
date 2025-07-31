package com.example.biblion.Activity.Dashboard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.biblion.Domain.BookModel
import com.example.biblion.Helper.FirebaseFavoritesHelper
import com.example.biblion.Helper.TinyDB
import com.example.biblion.Repository.BookRepository

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tinyDB = TinyDB(this)
        val userId = tinyDB.getString("user_email")

        setContent {
            FavoriteBooksScreen(
                userId = userId,
                onBackClick = { finish() }
            )
        }
    }
}