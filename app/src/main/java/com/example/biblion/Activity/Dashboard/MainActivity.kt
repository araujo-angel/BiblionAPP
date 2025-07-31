package com.example.biblion.Activity.Dashboard

import android.os.Bundle // importa a classe Bundle do Android, usada para passar dados na criação da activity
import androidx.activity.compose.setContent // importa a função setContent para usar Compose na activity
import androidx.activity.enableEdgeToEdge // importa a função para habilitar o conteúdo que vai até as bordas da tela
import androidx.appcompat.app.AppCompatActivity // importa a classe base para atividades compatíveis com ActionBar
import androidx.compose.foundation.layout.fillMaxSize // importa o modificador fillMaxSize para preencher espaço disponível
import androidx.compose.foundation.layout.padding // importa o modificador padding para adicionar espaçamento interno
import androidx.compose.foundation.lazy.LazyColumn // importa a LazyColumn, que cria uma lista rolável preguiçosa
import androidx.compose.material.Scaffold // importa o Scaffold, que fornece estrutura básica com barra, rodapé, etc.
import androidx.compose.material.rememberScaffoldState // importa para lembrar o estado do Scaffold
import androidx.compose.runtime.Composable // importa a anotação para funções Compose
import androidx.compose.runtime.LaunchedEffect // importa para executar efeitos colaterais ao iniciar a composição
import androidx.compose.runtime.getValue // importa para usar propriedade Delegates para estado
import androidx.compose.runtime.mutableStateListOf // importa para criar listas mutáveis que reagem às mudanças
import androidx.compose.runtime.mutableStateOf // importa para criar estados mutáveis simples
import androidx.compose.runtime.remember // importa para manter o estado durante a recomposição
import androidx.compose.runtime.setValue // importa para usar propriedade Delegates para estado
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier // importa a classe Modifier para modificar componentes
import com.example.biblion.Domain.BannerModel // importa o modelo de banner
import com.example.biblion.Domain.BookModel
import com.example.biblion.Domain.CategoryModel // importa o modelo de categoria
import com.example.biblion.Repository.BookRepository
import com.example.biblion.ViewModel.MainViewModel // importa o ViewModel principal

class MainActivity : AppCompatActivity() { // define a atividade principal estendendo AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) { // método chamado na criação da atividade
        super.onCreate(savedInstanceState) // chama o método pai para configuração padrão
//        enableEdgeToEdge() // habilita o conteúdo que vai até as bordas da tela
        setContent { // define o conteúdo da atividade usando Compose
            MainScreen() // chama a função Compose que monta a tela principal
        }
    }
}

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState()
    val viewModel = MainViewModel()
    val books = remember { mutableStateListOf<BookModel>() }
    val banners = remember { mutableStateListOf<BannerModel>() }
    val categories = remember { mutableStateListOf<CategoryModel>() }

    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }

    // Texto digitado no campo de busca
    var searchQuery by remember { mutableStateOf("") }

    // Lista filtrada com base na busca
    val filteredCategories = categories.filter {
        it.CategoryName.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.loadBooks().observeForever {
            books.clear()
            books.addAll(it)
            BookRepository.allBooks.clear()
            BookRepository.allBooks.addAll(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadBanner().observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCategory().observeForever {
            categories.clear()
            categories.addAll(it)
            showCategoryLoading = false
        }
    }

    Scaffold(
        bottomBar = { MyBottomBar() },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                TopBar()
            }
            item {
                Banner(banners, showBannerLoading)
            }
            item {
                SearchableCategorySection(categories, showCategoryLoading)
            }
        }
    }
}