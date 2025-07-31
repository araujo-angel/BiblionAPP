package com.example.biblion.ViewModel

import androidx.lifecycle.LiveData // Importa a classe LiveData para observar dados de forma reativa
import androidx.lifecycle.ViewModel // Importa a classe ViewModel para gerenciamento de dados na UI
import com.example.biblion.Domain.BannerModel // Importa o modelo de banner
import com.example.biblion.Domain.CategoryModel // Importa o modelo de categoria
import com.example.biblion.Domain.BookModel // Importa o modelo de comida
import com.example.biblion.Repository.MainRepository // Importa o repositório que busca os dados

class MainViewModel : ViewModel() { // Classe ViewModel que gerencia os dados para a UI
    private val repository = MainRepository() // Cria uma instância do repositório para buscar os dados

    fun loadBanner(): LiveData<MutableList<BannerModel>> { // Função para carregar a lista de banners
        return repository.loadBanner() // Retorna os banners obtidos pelo repositório
    }
    fun loadCategory(): LiveData<MutableList<CategoryModel>> { // Função para carregar a lista de categorias
        return repository.loadCategory() // Retorna as categorias obtidas pelo repositório
    }
    fun loadFiltered(id: String): LiveData<MutableList<BookModel>> { // Função para carregar alimentos filtrados por ID
        return repository.loadFiltered(id) // Retorna os alimentos filtrados pelo repositório
    }
    fun loadBooks(): LiveData<MutableList<BookModel>> {
        return repository.loadBooks() // vamos criar isso no repositório também
    }
}