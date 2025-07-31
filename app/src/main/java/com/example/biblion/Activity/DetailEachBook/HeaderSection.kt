package com.example.biblion.Activity.DetailEachBook

import androidx.compose.foundation.Image // importa o componente de imagem do Jetpack Compose
import androidx.compose.foundation.clickable // importa para tornar componentes clicáveis
import androidx.compose.foundation.layout.fillMaxWidth // importa para fazer o componente ocupar toda a largura disponível
import androidx.compose.foundation.layout.height // importa para definir a altura de componentes
import androidx.compose.foundation.layout.padding // importa para adicionar espaçamento interno
import androidx.compose.foundation.shape.RoundedCornerShape // importa para criar cantos arredondados
import androidx.compose.material3.Text // importa o componente de texto
import androidx.compose.runtime.Composable // importa para marcar funções como componentes Compose
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier // importa para modificar componentes visuais
import androidx.compose.ui.draw.clip // importa para recortar componentes com formas específicas
import androidx.compose.ui.layout.ContentScale // importa para ajustar o conteúdo da imagem
import androidx.compose.ui.res.colorResource // importa para usar cores definidas nos recursos
import androidx.compose.ui.res.painterResource // importa para carregar imagens dos recursos
import androidx.compose.ui.text.font.FontWeight // importa para definir peso da fonte
import androidx.compose.ui.unit.dp // importa para definir tamanhos em dp
import androidx.compose.ui.unit.sp // importa para definir tamanhos de fonte em sp
import androidx.constraintlayout.compose.ConstraintLayout // importa o layout que permite posicionar componentes com restrições
import coil.compose.rememberAsyncImagePainter // importa para carregar imagens assíncronas
import com.example.biblion.Domain.BookModel // importa o modelo de dados do livro
import com.example.biblion.R // importa recursos do projeto (como imagens e cores)
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.biblion.Helper.FirebaseFavoritesHelper

@Composable
fun HeaderSection(
    item: BookModel, // dado do livro a ser exibido
    numberInCart: Int, // quantidade de itens no carrinho
    userFavorites: List<String>,
    onBackClick: () -> Unit, // ação ao clicar no botão de voltar
    onIncrement: () -> Unit, // ação ao incrementar quantidade
    onDecrement: () -> Unit, // ação ao decrementar quantidade
    onToggleFavorite: (bookId: String, isNowFavorite: Boolean) -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(userFavorites) {
        isFavorite = userFavorites.contains(item.Id.toString())
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth() // ocupa toda a largura disponível
            .height(570.dp) // altura fixa de 570dp
            .padding(bottom = 16.dp) // espaçamento inferior de 16dp
    ) {
        val (back, fav, mainImage, arcImg, title, detailRow, numberRow) = createRefs() // define referências para os componentes

        Image(
            painter = rememberAsyncImagePainter(model = item.ImagePath), // carrega a imagem do livro de forma assíncrona
            contentDescription = null, // descrição da imagem (null aqui)
            contentScale = ContentScale.Crop, // ajusta a imagem para preencher o espaço cortando o excesso
            modifier = Modifier
                .fillMaxWidth() // ocupa toda a largura
                .height(400.dp) // altura de 400dp
                .clip(
                    RoundedCornerShape(
                        bottomStart = 30.dp, bottomEnd = 30.dp // cantos arredondados na parte inferior
                    )
                )
                .constrainAs(mainImage) {
                    top.linkTo(parent.top) // fixa no topo do layout
                    end.linkTo(parent.end) // fixa na extremidade direita
                    start.linkTo(parent.start) // fixa na extremidade esquerda
                }
        )
        Image(
            painter = painterResource(R.drawable.arc_bg), // imagem de fundo decorativa
            contentDescription = null,
            modifier = Modifier
                .height(190.dp) // altura de 190dp
                .constrainAs(arcImg) {
                    top.linkTo(mainImage.bottom, margin = (-64).dp) // posiciona sobre a imagem principal com margem negativa
                    end.linkTo(parent.end) // fixa na extremidade direita
                    start.linkTo(parent.start) // fixa na extremidade esquerda
                }
        )
        BackButton(onBackClick,Modifier.constrainAs(back){
            top.linkTo(parent.top) // fixa no topo
            start.linkTo(parent.start) // fixa na esquerda
        }) // botão de voltar

        FavoriteButton(
            isFavorite = isFavorite,
            onToggleFavorite = {
                isFavorite = !isFavorite
                onToggleFavorite(item.Id.toString(), isFavorite)
            },
            modifier = Modifier.constrainAs(fav) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }) // botão de favorito

        Text(
            text=item.Title, // título do livro
            fontSize = 24.sp, // tamanho da fonte de 24sp
            fontWeight = FontWeight.Bold, // fonte em negrito
            maxLines = 1, // limite de uma linha
            color = colorResource(R.color.darkPink), // cor do texto
            modifier = Modifier
                .padding(horizontal = 16.dp) // margem horizontal de 16dp
                .constrainAs(title){
                    top.linkTo(arcImg.top, margin = 32.dp) // posiciona abaixo da imagem decorativa com margem de 32dp
                    end.linkTo(parent.end) // fixa na extremidade direita
                    start.linkTo(parent.start) // fixa na esquerda
                }
        )
        RowDetail(item,Modifier.constrainAs(detailRow){
            top.linkTo(title.bottom) // posiciona abaixo do título
            start.linkTo(parent.start) // fixa na esquerda
            end.linkTo(parent.end) // fixa na direita
        }) // linha com detalhes do livro
        NumberRow(
            item=item, // dados do livro
            numberInCart=numberInCart, // quantidade no carrinho
            onIncrement=onIncrement, // ação de incrementar
            onDecrement=onDecrement, // ação de decrementar
            Modifier.constrainAs(numberRow){
                top.linkTo(detailRow.bottom) // posiciona abaixo dos detalhes
                bottom.linkTo(parent.bottom) // fixa na parte inferior
                start.linkTo(parent.start) // fixa na esquerda
                end.linkTo(parent.end) // fixa na direita
            }
        ) // componente para ajustar quantidade
    }
}

@Composable
private fun BackButton(onClick:()->Unit,modifier: Modifier=Modifier){
    Image(
        painter = painterResource(R.drawable.back), // imagem do botão de voltar
        contentDescription = null, // descrição (null aqui)
        modifier = modifier
            .padding(start = 16.dp,top=48.dp) // espaçamento interno: 16dp na esquerda e 48dp no topo
            .clickable { onClick() } // torna clicável e chama a ação ao clicar
    )
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = if (isFavorite) R.drawable.fav_filled else R.drawable.fav_icon

    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = modifier
            .padding(end = 16.dp, top = 48.dp)
            .clickable { onToggleFavorite() }
    )
}


