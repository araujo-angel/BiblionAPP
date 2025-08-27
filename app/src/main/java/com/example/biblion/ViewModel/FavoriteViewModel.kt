package com.example.biblion.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biblion.Domain.BookModel
import com.example.biblion.Repository.BookRepository
import com.example.biblion.Room.FavoriteBookEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface FavoriteUiState {
    object Loading : FavoriteUiState
    object Empty : FavoriteUiState
    data class Success(val books: List<FavoriteBookEntity>) : FavoriteUiState
    data class Error(val message: String) : FavoriteUiState
}

class FavoriteViewModel(
    private val bookRepository: BookRepository,
    private val userEmail: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Loading)
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            bookRepository.favoriteBooks.collect { favorites ->
                _uiState.value = if (favorites.isEmpty()) {
                    FavoriteUiState.Empty
                } else {
                    FavoriteUiState.Success(favorites)
                }
            }
        }
    }

    fun removeFavorite(bookId: String) {
        viewModelScope.launch {
            bookRepository.removeFromFavorites(bookId, userEmail)
        }
    }

    fun addFavorite(book: BookModel) {
        viewModelScope.launch {
            bookRepository.addToFavorites(book, userEmail)
        }
    }
}

