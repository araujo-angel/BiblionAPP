package com.example.biblion.ViewModel

import androidx.lifecycle.LiveData // Importa a classe LiveData para observar dados de forma reativa
import androidx.lifecycle.ViewModel // Importa a classe ViewModel para gerenciamento de dados na UI
import com.example.biblion.Domain.BannerModel // Importa o modelo de banner
import com.example.biblion.Domain.CategoryModel // Importa o modelo de categoria
import com.example.biblion.Domain.BookModel // Importa o modelo de comida
import com.example.biblion.Repository.MainRepository // Importa o repositório que busca os dados

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    fun loadBooks(): LiveData<MutableList<BookModel>> = repository.loadBooks()
    fun loadBanner(): LiveData<MutableList<BannerModel>> = repository.loadBanner()
    fun loadCategory(): LiveData<MutableList<CategoryModel>> = repository.loadCategory()
    fun loadFiltered(id: String): LiveData<MutableList<BookModel>> = repository.loadFiltered(id)
}