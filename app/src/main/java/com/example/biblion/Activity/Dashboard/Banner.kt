package com.example.biblion.Activity.Dashboard


import androidx.compose.foundation.background // Importa função para aplicar fundo colorido
import androidx.compose.foundation.interaction.collectIsDraggedAsState // Importa para detectar se o pager está sendo arrastado
import androidx.compose.foundation.layout.Box // Importa caixa para empacotar elementos
import androidx.compose.foundation.layout.Column // Importa coluna para empilhar elementos verticalmente
import androidx.compose.foundation.layout.Spacer // Importa espaço vazio entre elementos
import androidx.compose.foundation.layout.fillMaxSize // Importa para fazer o elemento ocupar todo espaço disponível
import androidx.compose.foundation.layout.height // Importa para definir altura de elementos
import androidx.compose.foundation.layout.padding // Importa para adicionar espaçamento interno
import androidx.compose.foundation.layout.size // Importa para definir tamanho de elementos
import androidx.compose.foundation.layout.wrapContentHeight // Importa para ajustar altura ao conteúdo
import androidx.compose.foundation.layout.wrapContentWidth // Importa para ajustar largura ao conteúdo
import androidx.compose.foundation.lazy.LazyRow // Importa linha preguiçosa para rolar elementos horizontalmente
import androidx.compose.foundation.shape.CircleShape // Importa forma circular
import androidx.compose.foundation.shape.RoundedCornerShape // Importa forma com cantos arredondados
import androidx.compose.material.CircularProgressIndicator // Importa indicador de progresso circular
import androidx.compose.runtime.Composable // Importa anotação para funções composáveis
import androidx.compose.runtime.getValue // Importa para obter valores de estado
import androidx.compose.runtime.remember // Importa para manter estado entre recomposições
import androidx.compose.runtime.snapshots.SnapshotStateList // Importa lista de estado que dispara recomposições
import androidx.compose.ui.Alignment // Importa para alinhamento de elementos
import androidx.compose.ui.Modifier // Importa para modificar elementos na interface
import androidx.compose.ui.draw.clip // Importa para recortar elementos em formas
import androidx.compose.ui.graphics.Color // Importa representação de cores
import androidx.compose.ui.layout.ContentScale // Importa para ajustar escala de conteúdo de imagem
import androidx.compose.ui.platform.LocalContext // Importa para acessar contexto atual
import androidx.compose.ui.res.colorResource // Importa para usar cores definidas nos recursos
import androidx.compose.ui.unit.Dp // Importa tipo de dado para unidades de dimensão
import androidx.compose.ui.unit.dp // Importa para definir dimensões em dp
import coil.compose.AsyncImage // Importa componente de imagem assíncrona
import coil.request.ImageRequest // Importa para criar pedidos de imagem
import com.google.accompanist.pager.ExperimentalPagerApi // Importa anotação experimental para pager
import com.google.accompanist.pager.HorizontalPager // Importa componente de pager horizontal
import com.google.accompanist.pager.PagerState // Importa estado do pager
import com.example.biblion.Domain.BannerModel // Importa o modelo de banner
import com.example.biblion.R // Importa recursos do projeto

@Composable
fun Banner(banners: SnapshotStateList<BannerModel>, showBannerLoading: Boolean) { // Função que exibe banner ou carregamento
    if (showBannerLoading) { // Se estiver carregando
        Box(
            modifier = Modifier
                .fillMaxSize() // Ocupa toda a tela
                .height(200.dp), // Com altura de 200dp
            contentAlignment = Alignment.Center // Centraliza o conteúdo dentro da box
        ) {
            CircularProgressIndicator() // Mostra indicador de progresso
        }
    } else { // Se não estiver carregando
        Banners(banners) // Mostra os banners
    }
}

@OptIn(ExperimentalPagerApi::class) // Indica uso de API experimental de pager
@Composable
fun Banners(banners: List<BannerModel>) { // Função que exibe uma lista de banners
    AutoSlidingCarosel(banners = banners) // Chama o carrossel automático com os banners
}

@OptIn(ExperimentalPagerApi::class) // Indica uso de API experimental de pager
@Composable
fun AutoSlidingCarosel(
    modifier: Modifier = Modifier, // Permite modificar o componente
    pagerState: PagerState = remember { PagerState() }, // Estado do pager, lembrado entre recomposições
    banners: List<BannerModel> // Lista de banners a mostrar
) {

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState() // Detecta se o usuário está arrastando o pager

    Column(modifier = modifier.fillMaxSize()) { // Empilha elementos verticalmente e ocupa toda a tela
        HorizontalPager(count = banners.size, state = pagerState) { page -> // Cria o pager horizontal com quantidade de banners
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current) // Cria requisição de imagem
                    .data(banners[page].image) // Define a URL ou recurso da imagem do banner atual
                    .build(), // Finaliza a requisição
                contentDescription = null, // Sem descrição de acessibilidade
                contentScale = ContentScale.FillBounds, // A imagem preenche toda a área do componente
                modifier = Modifier
                    .padding(16.dp) // Espaçamento interno de 16dp
                    .clip(RoundedCornerShape(10.dp)) // Cantos arredondados com raio de 10dp
                    .height(150.dp) // Altura de 150dp
            )
        }
        DotIndocator(
            modifier = Modifier
                .padding(horizontal = 8.dp) // Espaçamento horizontal de 8dp
                .align(Alignment.CenterHorizontally), // Centraliza horizontalmente
            totalDots = banners.size, // Número total de pontos (um por banner)
            selectedIndex = if (isDragged) pagerState.currentPage else pagerState.currentPage, // Índice do ponto selecionado (sempre atual)
            dotSize = 8.dp // Tamanho dos pontos
        )

    }
}


@Composable
fun DotIndocator(
    modifier: Modifier = Modifier, // Modificador padrão
    totalDots: Int, // Quantidade total de pontos
    selectedIndex: Int, // Índice do ponto selecionado
    selectedColor: Color = colorResource(R.color.pink), // Cor do ponto selecionado
    unSelectedColor: Color = colorResource(R.color.grey), // Cor dos pontos não selecionados
    dotSize: Dp // Tamanho dos pontos
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth() // Ajusta a largura ao conteúdo
            .wrapContentHeight() // Ajusta a altura ao conteúdo
    ) {
        items(totalDots) { index -> // Para cada ponto
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor, // Cor do ponto dependendo se está selecionado
                size = dotSize // Tamanho do ponto
            )
            if (index != totalDots - 1) { // Se não for o último ponto
                Spacer(modifier = Modifier.padding(horizontal = 2.dp)) // Espaçamento entre pontos
            }
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier, // Modificador padrão
    size: Dp, // Tamanho do ponto
    color: Color // Cor do ponto
) {
    Box(
        modifier = modifier
            .size(size) // Define tamanho do ponto
            .clip(CircleShape) // Forma circular
            .background(color) // Cor de fundo do ponto
    )
}