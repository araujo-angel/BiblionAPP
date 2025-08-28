package com.example.biblion.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.biblion.Domain.BookModel
import com.example.biblion.Helper.FirebaseFavoritesHelper
import com.example.biblion.Room.FavoriteBookDao
import com.example.biblion.Room.FavoriteBookEntity
import kotlinx.coroutines.flow.Flow

class BookRepository(
    val favoriteBookDao: FavoriteBookDao,
    private val mainRepository: MainRepository
) {
    val favoriteBooks: Flow<List<FavoriteBookEntity>> = favoriteBookDao.getAll()

    fun loadBooks(): LiveData<MutableList<BookModel>> = mainRepository.loadBooks()

    fun getBookById(bookId: String): LiveData<BookModel?> {
        return mainRepository.getBookById(bookId)
    }

    suspend fun addToFavorites(book: BookModel, userEmail: String) {
        val entity = FavoriteBookEntity(
            bookId = book.Id.toString(),
            title = book.Title,
            pages = book.Paginas.toString(),
            imagePath = book.ImagePath,
            price = book.Price
        )
        favoriteBookDao.insert(entity)

        // Atualiza Firebase
        FirebaseFavoritesHelper.toggleFavorite(userEmail, book.Id.toString(), true)
    }

    suspend fun removeFromFavorites(bookId: String, userEmail: String) {
        val entity = favoriteBookDao.getById(bookId)
        entity?.let {
            favoriteBookDao.delete(it)
            // Atualiza Firebase
            FirebaseFavoritesHelper.toggleFavorite(userEmail, bookId, false)
        }
    }

    suspend fun isFavorite(bookId: String): Boolean {
        return favoriteBookDao.getById(bookId) != null
    }

    /**
     * Busca favoritos do Firebase e devolve para o ViewModel
     * (quem chama decide como salvar no Room).
     */
    fun fetchFavoritesFromFirebase(
        userEmail: String,
        onResult: (List<String>) -> Unit
    ) {
        FirebaseFavoritesHelper.getUserFavorites(userEmail) { favoriteIds ->
            onResult(favoriteIds)
        }
    }

}
