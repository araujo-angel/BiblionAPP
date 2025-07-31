package com.example.biblion.Repository

import com.example.biblion.Domain.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class UserRepository {
    private val db = FirebaseFirestore.getInstance()

    fun login(
        email: String,
        password: String,
        onSuccess: (UserModel) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("users")
            .document(email)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(UserModel::class.java)
                    if (user != null && user.password == password) {
                        onSuccess(user)
                    } else {
                        onFailure("Credenciais inválidas")
                    }
                } else {
                    onFailure("Usuário não encontrado")
                }
            }
            .addOnFailureListener { e ->
                onFailure("Erro no login: ${e.message}")
            }
    }

    fun register(
        user: UserModel,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("users")
            .document(user.email)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener { e ->
                onResult(false)
            }
    }

    fun updateFavorites(
        userEmail: String, // Modificado: usa email como ID
        newFavorites: List<String>,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("users")
            .document(userEmail)
            .update("favorites", newFavorites)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}