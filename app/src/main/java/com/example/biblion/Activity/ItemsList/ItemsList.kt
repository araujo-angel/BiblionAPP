package com.example.biblion.Activity.ItemsList

import android.content.Intent // Importa para criar intents que iniciam atividades
import androidx.compose.foundation.Image // Para exibir imagens
import androidx.compose.foundation.background // Para definir o fundo de componentes
import androidx.compose.foundation.clickable // Torna componentes clicáveis
import androidx.compose.foundation.layout.Column // Empilha componentes verticalmente
import androidx.compose.foundation.layout.Row // Alinha componentes horizontalmente
import androidx.compose.foundation.layout.RowScope // Escopo de Row
import androidx.compose.foundation.layout.fillMaxHeight // Para preencher altura disponível
import androidx.compose.foundation.layout.fillMaxWidth // Para preencher largura disponível
import androidx.compose.foundation.layout.padding // Para adicionar espaçamento interno
import androidx.compose.foundation.layout.size // Para definir tamanho de componentes
import androidx.compose.foundation.layout.wrapContentHeight // Ajusta a altura ao conteúdo
import androidx.compose.foundation.lazy.LazyColumn // Lista que carrega itens sob demanda
import androidx.compose.foundation.lazy.itemsIndexed // Para iterar itens com índice
import androidx.compose.foundation.shape.RoundedCornerShape // Para cantos arredondados
import androidx.compose.material3.MaterialTheme // Tema padrão do Material Design
import androidx.compose.material3.Text // Componente de texto
import androidx.compose.runtime.Composable // Para funções que gerenciam o estado da UI
import androidx.compose.ui.Alignment // Alinhamento de componentes
import androidx.compose.ui.Modifier // Modificador para alterar aparência e comportamento
import androidx.compose.ui.draw.clip // Para recortar componentes com formas
import androidx.compose.ui.layout.ContentScale // Para ajustar escala de imagens
import androidx.compose.ui.platform.LocalContext // Para obter o contexto atual
import androidx.compose.ui.res.colorResource // Para obter cores definidas nos recursos
import androidx.compose.ui.res.painterResource // Para carregar recursos de imagem
import androidx.compose.ui.text.font.FontWeight // Para definir peso da fonte
import androidx.compose.ui.text.style.TextOverflow // Para lidar com texto que passa do limite
import androidx.compose.ui.unit.dp // Unidade de medida de densidade
import androidx.compose.ui.unit.sp // Unidade de medida de tamanho de fonte
import androidx.core.content.ContextCompat.startActivity // Para iniciar nova activity
import coil.compose.AsyncImage // Carregamento assíncrono de imagens
import com.example.biblion.Activity.DetailEachBook.DetailEachBookActivity // Activity de detalhes do alimento
import com.example.biblion.Domain.BookModel // Modelo de dados do alimento
import com.example.biblion.R // Recursos do projeto

@Composable
fun ItemsList(items: List<BookModel>) { // Função que exibe uma lista de alimentos
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) { // Lista rolável com padding
        itemsIndexed(items) { index, item -> // Para cada item, com seu índice
            Items(item = item, index = index) // Chama o componente que exibe o item
        }
    }
}

@Composable
fun Items(item: BookModel, index: Int) { // Componente que exibe um item individual
    val context = LocalContext.current // Obtém o contexto atual
    val isEvenRow = index % 2 == 0 // Verifica se a linha é par para alternar a disposição

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp) // Espaçamento vertical entre itens
            .fillMaxWidth() // Largura total disponível
            .background(colorResource(R.color.grey), shape = RoundedCornerShape(10.dp)) // Fundo cinza com cantos arredondados
            .wrapContentHeight() // Altura ajustada ao conteúdo
            .clickable { // Torna a linha clicável
                val intent = Intent(context, DetailEachBookActivity::class.java).apply { // Cria intent para abrir detalhes
                    putExtra("object", item) // Passa o objeto alimento para a próxima tela
                }
                startActivity(context, intent, null) // Inicia a atividade de detalhes
            }
    ) {
        if (isEvenRow) { // Se for linha par
            BookImage(item = item) // Mostra a imagem primeiro
            BookDetails(item = item) // Depois os detalhes
        } else { // Se for linha ímpar
            BookDetails(item = item) // Mostra os detalhes primeiro
            BookImage(item = item) // Depois a imagem
        }
    }
}

@Composable
fun BookImage(item: BookModel) { // Componente que exibe a imagem do alimento
    AsyncImage(
        model = item.ImagePath, // Caminho da imagem do alimento
        contentDescription = null, // Descrição da imagem (não fornecida)
        modifier = Modifier
            .size(120.dp) // Tamanho da imagem
            .clip(RoundedCornerShape(10.dp)) // Cantos arredondados
            .background(colorResource(R.color.grey), shape = RoundedCornerShape(10.dp)), // Fundo cinza
        contentScale = ContentScale.Crop // Ajusta para preencher o espaço cortando o excesso
    )
}

@Composable
fun RowScope.BookDetails(item: BookModel) { // Componente que mostra detalhes do alimento
    Column(
        modifier = Modifier
            .padding(start = 8.dp) // Espaçamento à esquerda
            .fillMaxHeight() // Preenche altura do pai
            .weight(1f) // Distribui espaço proporcionalmente
    ) {
        Text(
            text = item.Title, // Nome do alimento
            color = colorResource(R.color.black), // Cor do texto
            fontSize = 16.sp, // Tamanho da fonte
            fontWeight = FontWeight.SemiBold, // Peso semi negrito
            maxLines = 1, // Limita a uma linha
            overflow = TextOverflow.Ellipsis, // Trunca o texto com reticências se passar do limite
            modifier = Modifier.padding(top = 8.dp) // Espaçamento superior
        )
        TimingRow(item.Paginas) // Mostra o tempo de preparo
        RatingBarRow(item.TempoLeitura) // Mostra a avaliação por estrelas
        Text(
            text = "$${item.Price}", // Preço do alimento
            color = colorResource(R.color.black), // Cor do texto
            fontSize = 18.sp, // Tamanho da fonte
            fontWeight = FontWeight.SemiBold, // Peso semi negrito
            modifier = Modifier.padding(top = 8.dp) // Espaçamento superior
        )
    }
}

@Composable
fun RatingBarRow(star: Double) { // Componente que mostra a avaliação
    Row(
        verticalAlignment = Alignment.CenterVertically, // Alinha verticalmente ao centro
        modifier = Modifier.padding(top = 8.dp) // Espaçamento superior
    ) {
        Image(
            painter = painterResource(R.drawable.star), // Ícone de estrela
            contentDescription = null, // Descrição da imagem (não fornecida)
            modifier = Modifier.padding(end = 8.dp) // Espaçamento à direita
        )
        Text(text = "$star", style = MaterialTheme.typography.bodyMedium) // Texto com a avaliação
    }
}

@Composable
fun TimingRow(timeValue: Int) { // Componente que mostra o tempo de preparo
    Row(
        verticalAlignment = Alignment.CenterVertically, // Alinha ao centro vertical
        modifier = Modifier.padding(top = 8.dp) // Espaçamento superior
    ) {
        Image(
            painter = painterResource(R.drawable.time), // Ícone de tempo
            contentDescription = null, // Descrição da imagem
            modifier = Modifier.padding(end = 8.dp) // Espaçamento à direita
        )
        Text(text = "$timeValue min", style = MaterialTheme.typography.bodyMedium) // Texto do tempo
    }
}