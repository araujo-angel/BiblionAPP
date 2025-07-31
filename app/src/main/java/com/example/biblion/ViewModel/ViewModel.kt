package com.example.biblion.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.example.biblion.Domain.UserModel
import com.example.biblion.Repository.UserRepository

class UserViewModel : ViewModel() {
    private val repo = UserRepository()
    val user = MutableLiveData<UserModel?>()
    val error = MutableLiveData<String>()

    fun login(email: String, password: String) {
        repo.login(email, password, {
            user.value = it
        }, {
            error.value = it
        })
    }

    fun register(userModel: UserModel) {
        repo.register(userModel) {
            if (it) user.value = userModel
            else error.value = "Erro ao registrar"
        }
    }

    fun updateFavorites(userEmail: String, newFavorites: List<String>) {
        repo.updateFavorites(userEmail, newFavorites) { success ->
            if (success) {
                user.value = user.value?.copy(favorites = newFavorites)
            }
        }
    }

}