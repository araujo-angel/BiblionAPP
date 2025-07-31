package com.example.biblion.Activity.DetailEachBook

import androidx.compose.foundation.Image // Importa componente para exibir imagens
import androidx.compose.foundation.layout.Row // Importa componente para layout em linha
import androidx.compose.foundation.layout.Spacer // Importa componente para espaço entre elementos
import androidx.compose.foundation.layout.padding // Importa função para adicionar padding (espaçamento interno)
import androidx.compose.foundation.layout.width // Importa função para definir largura do Spacer
import androidx.compose.material.Text // Importa componente para exibir textos
import androidx.compose.runtime.Composable // Importa anotação para funções que compõem a UI
import androidx.compose.ui.Alignment // Importa opções de alinhamento
import androidx.compose.ui.Modifier // Importa para modificar componentes
import androidx.compose.ui.res.colorResource // Importa para usar cores definidas em recursos
import androidx.compose.ui.res.painterResource // Importa para usar imagens dos recursos
import androidx.compose.ui.text.font.FontWeight // Importa para definir peso da fonte (negrito)
import androidx.compose.ui.unit.dp // Importa unidade de medida para padding e tamanhos
import androidx.compose.ui.unit.sp // Importa unidade de medida para tamanho de fonte
import com.example.biblion.Domain.BookModel // Importa o modelo de dados BookModel
import com.example.biblion.R // Importa recursos do projeto

@Composable
fun RowDetail(item: BookModel, modifier: Modifier = Modifier) { // Define uma função composable que exibe detalhes em linha, recebendo um item de BookModel e um modificador opcional
    Row(modifier = modifier.padding(top = 32.dp), verticalAlignment = Alignment.CenterVertically) { // Cria uma linha com padding superior de 32dp e alinhamento vertical ao centro
        Image(painter = painterResource(R.drawable.baseline_article_24), contentDescription = null) // Exibe uma imagem de tempo (ícone), sem descrição de conteúdo
        Text(
            "${item.Paginas} pgs", // Exibe o tempo do item seguido de 'min'
            modifier = Modifier.padding(start = 8.dp), // Adiciona padding à esquerda do texto
            fontWeight = FontWeight.Bold, // Deixa o texto em negrito
            fontSize = 15.sp, // Define o tamanho da fonte
            color = colorResource(R.color.black) // Define a cor do texto
        )
        Spacer(modifier = Modifier.width(32.dp)) // Espaço de 32dp entre os elementos
        Image(painter = painterResource(R.drawable.baseline_access_time_24), contentDescription = null) // Exibe uma imagem de estrela
        Text(
            "${item.TempoLeitura} ", // Exibe a quantidade de estrelas do item
            modifier = Modifier.padding(start = 8.dp), // Padding à esquerda
            fontWeight = FontWeight.Bold, // Texto em negrito
            fontSize = 15.sp, // Tamanho da fonte
            color = colorResource(R.color.black) // Cor do texto
        )
        Spacer(modifier = Modifier.width(32.dp)) // Espaço de 32dp
        Image(painter = painterResource(R.drawable.baseline_calendar_today_24), contentDescription = null) // Exibe uma imagem de fogo (calorias)
        Text(
            "${item.Ano} ", // Exibe a quantidade de calorias
            modifier = Modifier.padding(start = 8.dp), // Padding à esquerda
            fontWeight = FontWeight.Bold, // Texto em negrito
            fontSize = 15.sp, // Tamanho da fonte
            color = colorResource(R.color.black) // Cor do texto
        )
    }
}