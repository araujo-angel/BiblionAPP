package com.example.biblion.Helper

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseFavoritesHelper {
    private val db = FirebaseFirestore.getInstance()

    fun toggleFavorite(userEmail: String, bookId: String, isFavorite: Boolean) {
        if (userEmail.isBlank()) {
            Log.e("FAV", "Email do usuário vazio!")
            return
        }

        Log.d("FAV", "Atualizando favoritos: $userEmail | $bookId | $isFavorite")

        val update = if (isFavorite) {
            mapOf("favorites" to FieldValue.arrayUnion(bookId))
        } else {
            mapOf("favorites" to FieldValue.arrayRemove(bookId))
        }

        db.collection("users").document(userEmail)
            .update(update)
            .addOnSuccessListener {
                Log.d("FAV", "Favorito atualizado com sucesso")
            }
            .addOnFailureListener { e ->
                Log.e("FAV", "Erro ao atualizar favorito", e)
            }
    }

    fun getUserFavorites(userEmail: String, onResult: (List<String>) -> Unit) {
        if (userEmail.isBlank()) {
            Log.w("FAV", "Email vazio ao buscar favoritos")
            onResult(emptyList())
            return
        }

        db.collection("users").document(userEmail)
            .get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    Log.d("FAV", "Documento não existe: $userEmail")
                    onResult(emptyList())
                    return@addOnSuccessListener
                }

                // Tratamento de tipo seguro:
                val favorites = when (val data = document.get("favorites")) {
                    is List<*> -> data.filterIsInstance<String>()
                    else -> emptyList()
                }

                Log.d("FAV", "Favoritos carregados (${favorites.size}): $userEmail")
                onResult(favorites)
            }
            .addOnFailureListener { e ->
                Log.e("FAV", "Erro ao buscar favoritos", e)
                onResult(emptyList())
            }
    }
}