package com.example.biblion.Activity.DetailEachBook

import android.os.Bundle // Importa a classe Bundle do Android para passar dados na atividade
import android.util.Log
import androidx.activity.compose.setContent // Permite definir conteúdo Compose na atividade
import androidx.appcompat.app.AppCompatActivity // Classe base para atividades compatíveis com ActionBar
import androidx.compose.foundation.background // Permite definir fundo de elementos na interface
import androidx.compose.foundation.layout.Column // Container que organiza elementos em coluna
import androidx.compose.foundation.layout.fillMaxSize // Faz um elemento ocupar toda a tela disponível
import androidx.compose.foundation.layout.padding // Adiciona espaçamento interno nos elementos
import androidx.compose.foundation.rememberScrollState // Mantém o estado da rolagem
import androidx.compose.foundation.verticalScroll // Permite rolar conteúdo verticalmente
import androidx.compose.runtime.Composable // Marca funções como componentes Compose
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue // Permite obter valor de um estado
import androidx.compose.runtime.mutableStateOf // Cria um estado mutável
import androidx.compose.runtime.remember // Mantém o estado entre recomposições
import androidx.compose.runtime.setValue // Permite definir valor de um estado
import androidx.compose.ui.Modifier // Permite modificar elementos na interface
import androidx.compose.ui.graphics.Color // Define cores na interface
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp // Define unidades de densidade
import androidx.constraintlayout.compose.ConstraintLayout // Layout que permite posicionar elementos com restrições
import com.example.biblion.Helper.ManagementCart // Classe que gerencia o carrinho de compras
import com.example.biblion.Domain.BookModel // Modelo de dados do alimento
import com.example.biblion.Helper.FirebaseFavoritesHelper
import com.example.biblion.Helper.TinyDB

class DetailEachBookActivity : AppCompatActivity() { // Define a atividade que mostra detalhes de um livro
    private lateinit var item: BookModel // Declara variável que armazena o livro, será inicializada depois
    private lateinit var managementCart: ManagementCart // Declara gerenciador do carrinho, também será inicializado

    override fun onCreate(savedInstanceState: Bundle?) { // Método chamado ao criar a atividade
        super.onCreate(savedInstanceState) // Chama método da superclasse

        item = intent.getSerializableExtra("object") as BookModel // Recebe o objeto do livro enviado pela intent
        item.numberInCart = 1 // Define a quantidade inicial no carrinho como 1
        managementCart = ManagementCart(this) // Instancia o gerenciador do carrinho passando o contexto

        setContent { // Define o conteúdo da tela usando Compose
            DetailScreen( // Chama a função que monta a tela de detalhes
                item = item, // Passa o livro
                onBackClick = { finish() }, // Ao clicar voltar, encerra a atividade
                onAddToCartClick = {
                    managementCart.insertItem(item) // Adiciona o item ao carrinho
                }
            )
        }
    }
}

@Composable // Indica que a função é um componente Compose
private fun DetailScreen(
    item: BookModel, // livro a ser exibido
    onBackClick: () -> Unit, // Função ao clicar para voltar
    onAddToCartClick: () -> Unit // Função ao clicar para adicionar ao carrinho
) {
    var favorites by remember { mutableStateOf<List<String>>(emptyList()) }
    var numberInCart by remember { mutableStateOf(item.numberInCart) } // Mantém quantidade no carrinho como estado
    val context = LocalContext.current
    val userId = remember { TinyDB(context).getString("user_email") }

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            FirebaseFavoritesHelper.getUserFavorites(userId) { favList ->
                favorites = favList
            }
        } else {
            Log.w("FAV", "Usuário não logado ao tentar acessar favoritos")
        }
    }
    ConstraintLayout { // Layout que organiza elementos com restrições
        val (footer, column) = createRefs() // Cria referências para posicionar elementos
        Column(modifier = Modifier // Container vertical para conteúdo
            .fillMaxSize() // Ocupa toda a tela
            .background(Color.White) // Fundo branco
            .verticalScroll(rememberScrollState()) // Permite rolar o conteúdo
            .constrainAs(column) { // Vincula a coluna às restrições
                top.linkTo(parent.top) // Topo ligado ao topo do pai
                end.linkTo(parent.end) // Direita ligada à direita do pai
                start.linkTo(parent.start) // Esquerda ligada à esquerda do pai
            }
            .padding(bottom = 80.dp) // Espaçamento inferior de 80dp
        ) {

            HeaderSection(
                item = item,
                numberInCart = numberInCart,
                userFavorites = favorites,
                onBackClick = onBackClick,
                onIncrement = {
                    numberInCart++
                    item.numberInCart = numberInCart
                },
                onDecrement = {
                    if (numberInCart > 1) {
                        numberInCart--
                        item.numberInCart = numberInCart
                    }
                },
                onToggleFavorite = { bookId, isNowFavorite ->
                    if (userId.isNotEmpty()) {
                        FirebaseFavoritesHelper.toggleFavorite(userId, bookId as String, isNowFavorite as Boolean)
                        // Atualização local
                        favorites = if (isNowFavorite) {
                            favorites + bookId as String
                        } else {
                            favorites - (bookId as String)
                        }
                    } else {
                        Log.w("FAV", "Tentativa de favoritar sem usuário logado")
                    }
                }
            )
            DescriptionSection(item.Description)
        }
        FooterSection(
            onAddToCartClick, // Função ao clicar para adicionar ao carrinho
            totalPrice = (item.Price*numberInCart), // Preço total calculado
            Modifier.constrainAs(footer){ // Constrói o rodapé com restrições
                bottom.linkTo(parent.bottom) // Ligado à parte inferior do pai
                end.linkTo(parent.end) // Ligado à direita do pai
                start.linkTo(parent.start) // Ligado à esquerda do pai
            }
        )
    }
}