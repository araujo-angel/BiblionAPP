package com.example.biblion.Activity.ItemsList

import android.os.Bundle // importa a classe Bundle para passar dados entre telas
import androidx.activity.compose.setContent // importa para definir o conteúdo da activity usando Compose
import androidx.appcompat.app.AppCompatActivity // importa a classe base de uma activity compatível
import androidx.compose.foundation.Image // importa o componente de imagem do Compose
import androidx.compose.foundation.clickable // permite que elementos sejam clicáveis
import androidx.compose.foundation.layout.Box // container que sobrepõe elementos
import androidx.compose.foundation.layout.Column // container que organiza elementos em coluna
import androidx.compose.foundation.layout.fillMaxSize // modifica para preencher toda a tela
import androidx.compose.foundation.layout.fillMaxWidth // modifica para preencher toda a largura
import androidx.compose.foundation.layout.padding // adiciona espaço interno aos elementos
import androidx.compose.material.CircularProgressIndicator // componente de carregamento circular
import androidx.compose.material3.Text // componente de texto
import androidx.compose.runtime.Composable // marca funções como componentes do Compose
import androidx.compose.runtime.LaunchedEffect // efeito que roda ao lançar a composição
import androidx.compose.runtime.getValue // para usar propriedades observáveis
import androidx.compose.runtime.livedata.observeAsState // observa dados do LiveData como estado
import androidx.compose.runtime.mutableStateOf // cria um estado mutável
import androidx.compose.runtime.remember // lembra o estado entre recomposições
import androidx.compose.runtime.setValue // define valores em estados observáveis
import androidx.compose.ui.Alignment // alinhamento de elementos
import androidx.compose.ui.Modifier // permite modificar componentes
import androidx.compose.ui.res.painterResource // carrega recursos de imagem
import androidx.compose.ui.text.font.FontWeight // peso da fonte do texto
import androidx.compose.ui.text.style.TextAlign // alinhamento do texto
import androidx.compose.ui.unit.dp // unidade de medida de densidade
import androidx.compose.ui.unit.sp // unidade de tamanho de fonte
import androidx.constraintlayout.compose.ConstraintLayout // layout que usa restrições
import com.example.biblion.R // recursos do projeto
import com.example.biblion.ViewModel.MainViewModel // ViewModel que gerencia dados

class ItemsListActivity : AppCompatActivity() { // classe que representa a tela de lista de itens
    private val viewModel = MainViewModel() // instancia o ViewModel para gerenciar os dados
    private var id: String = "" // variável para guardar o id passado na intent
    private var title: String = "" // variável para guardar o título passado na intent

    override fun onCreate(savedInstanceState: Bundle?) { // método chamado ao criar a activity
        super.onCreate(savedInstanceState) // chama a implementação pai
        id = intent.getStringExtra("id") ?: "" // pega o valor do "id" da intent, se não houver, fica vazio
        title = intent.getStringExtra("title") ?: "" // pega o "title" da intent, se não houver, fica vazio

        setContent { // define o conteúdo da tela usando Compose
            ItemsListScreen( // chama a função que monta a tela
                title = title, // passa o título
                onBackClick = { finish() }, // ação ao clicar no botão de voltar
                viewModel = viewModel, // passa o ViewModel
                id = id // passa o id
            )
        }
    }
}

@Composable // marca a função como componente do Compose
private fun ItemsListScreen(
    title: String, // título a ser exibido
    onBackClick: () -> Unit, // função para chamar ao clicar no botão de voltar
    viewModel: MainViewModel, // ViewModel que fornece os dados
    id: String // id para filtrar os itens
) {
    val items by viewModel.loadFiltered(id).observeAsState(emptyList()) // observa a lista de itens filtrados pelo id
    var isLoading by remember { mutableStateOf(true) } // estado para controlar se está carregando

    LaunchedEffect(id) { // roda ao mudar o id
        viewModel.loadFiltered(id) // carrega os itens filtrados (pode ser redundante aqui)
    }
    LaunchedEffect(items) { // roda sempre que items mudar
        isLoading = items.isEmpty() // se a lista de itens estiver vazia, mostra carregando
    }

    Column(modifier = Modifier.fillMaxSize()) { // coluna que ocupa toda a tela
        ConstraintLayout( // layout que usa restrições
            modifier = Modifier.padding(top = 36.dp, start = 16.dp, end = 16.dp) // espaçamento interno superior e nas laterais
        ) {
            val (backBtn, cartTxt) = createRefs() // cria referências para os componentes
            Text(
                modifier = Modifier
                    .fillMaxWidth() // ocupa toda a largura
                    .constrainAs(cartTxt) { centerTo(parent) }, // centraliza o texto
                textAlign = TextAlign.Center, // centraliza o texto
                fontWeight = FontWeight.Bold, // deixa o texto em negrito
                fontSize = 25.sp, // tamanho da fonte
                text = title // texto a ser exibido (título)
            )
            Image(
                painter = painterResource(R.drawable.back_grey), // imagem de seta de voltar
                contentDescription = null, // descrição para acessibilidade (nulo aqui)
                modifier = Modifier
                    .clickable { // torna clicável
                        onBackClick() // chama a função de voltar
                    }
                    .constrainAs(backBtn) { // restrição para posicionar o botão
                        top.linkTo(parent.top) // alinhado ao topo do layout
                        bottom.linkTo(parent.bottom) // alinhado à parte de baixo
                        start.linkTo(parent.start) // alinhado ao lado esquerdo
                    }
            )
        }
        if (isLoading) { // se estiver carregando
            Box(
                modifier = Modifier.fillMaxSize(), // ocupa toda a tela
                contentAlignment = Alignment.Center // centraliza o conteúdo
            ) {
                CircularProgressIndicator() // mostra o indicador de carregamento
            }
        } else { // se não estiver carregando
            ItemsList(items) // mostra a lista de itens (função presumida)
        }
    }
}