package com.example.biblion.Activity.Cart

import androidx.compose.foundation.Image // Importa componente de imagem
import androidx.compose.foundation.background // Permite colocar cor de fundo
import androidx.compose.foundation.border // Permite adicionar borda
import androidx.compose.foundation.clickable // Permite fazer componentes clicáveis
import androidx.compose.foundation.layout.Box // Container para agrupar elementos
import androidx.compose.foundation.layout.fillMaxWidth // Faz ocupar toda a largura disponível
import androidx.compose.foundation.layout.height // Define altura
import androidx.compose.foundation.layout.padding // Espaçamento interno
import androidx.compose.foundation.layout.size // Define tamanho (largura e altura iguais)
import androidx.compose.foundation.layout.width // Define largura
import androidx.compose.foundation.shape.RoundedCornerShape // Forma arredondada para cantos
import androidx.compose.material3.Text // Texto na interface
import androidx.compose.runtime.Composable // Marca função como componente do Compose
import androidx.compose.runtime.getValue // Para obter valor de estado
import androidx.compose.runtime.mutableStateOf // Cria estado mutável
import androidx.compose.runtime.remember // Para lembrar o estado entre recomposições
import androidx.compose.runtime.setValue // Para definir valor de estado
import androidx.compose.runtime.snapshots.SnapshotStateList // Lista que reage a mudanças
import androidx.compose.ui.Alignment // Alinhamento de elementos
import androidx.compose.ui.Modifier // Modificador de componentes
import androidx.compose.ui.draw.clip // Recortar componentes com formas
import androidx.compose.ui.layout.ContentScale // Escala da imagem
import androidx.compose.ui.res.colorResource // Cores dos recursos
import androidx.compose.ui.text.font.FontWeight // Peso da fonte
import androidx.compose.ui.text.style.TextAlign // Alinhamento do texto
import androidx.compose.ui.unit.dp // Unidade de medida (density-independent pixels)
import androidx.compose.ui.unit.sp // Unidade de tamanho de fonte
import androidx.constraintlayout.compose.ConstraintLayout // Layout com restrições
import coil.compose.rememberAsyncImagePainter // Para carregar imagens assincronamente
import com.example.biblion.Helper.ManagementCart // Gerenciamento do carrinho
import com.example.biblion.Domain.BookModel // Modelo de dados do livro
import com.example.biblion.R // Recursos do projeto
import java.text.DecimalFormat // Formatação de números decimais
import java.util.ArrayList // Lista mutável

@Composable
fun CartItem(
    cartItems: SnapshotStateList<BookModel>, // Lista de itens do carrinho
    item: BookModel, // Item atual a ser exibido
    managementCart: ManagementCart, // Gerenciador do carrinho
    onItemChange: () -> Unit // Função para atualizar a interface ao mudar o item
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(vertical = 8.dp) // Espaçamento vertical entre itens
            .fillMaxWidth() // Ocupa toda a largura disponível
            .border(1.dp, colorResource(R.color.grey), shape = RoundedCornerShape(10.dp)) // Borda arredondada
    ) {
        val (pic, titleTxt, feeEachTime, totalEachItem, quantity) = createRefs() // Referências para os componentes
        var numberInCart by remember { mutableStateOf(item.numberInCart) } // Estado do número no carrinho
        val decimalFormat = DecimalFormat("#.00") // Formata valores monetários com duas casas decimais

        Image(
            painter = rememberAsyncImagePainter(item.ImagePath), // Carrega a imagem do produto
            contentDescription = null, // Sem descrição de acessibilidade
            contentScale = ContentScale.Crop, // Recorta a imagem para caber
            modifier = Modifier
                .width(135.dp) // Largura da imagem
                .height(100.dp) // Altura da imagem
                .background(
                    colorResource(R.color.grey), // Cor de fundo
                    shape = RoundedCornerShape(10.dp) // Cantos arredondados
                )
                .clip(RoundedCornerShape(10.dp)) // Recorta a imagem com cantos arredondados
                .constrainAs(pic) { // Regras de posição no layout
                    start.linkTo(parent.start) // Alinha ao início do pai
                    top.linkTo(parent.top) // Alinha ao topo do pai
                    bottom.linkTo(parent.bottom) // Alinha ao fundo do pai
                }
        )

        Text(
            text = item.Title, // Nome do produto
            fontWeight = FontWeight.Bold, // Texto em negrito
            fontSize = 16.sp, // Tamanho da fonte
            modifier = Modifier
                .constrainAs(titleTxt) {
                    start.linkTo(pic.end) // Começa após a imagem
                    top.linkTo(pic.top) // Alinha ao topo da imagem
                }
                .padding(start = 8.dp, top = 8.dp) // Espaçamento interno
        )

        Text(
            text = "$${decimalFormat.format(item.Price)}", // Preço do item formatado
            fontSize = 16.sp, // Tamanho da fonte
            color = colorResource(R.color.darkPurple), // Cor do texto
            modifier = Modifier
                .constrainAs(feeEachTime) {
                    start.linkTo(titleTxt.start) // Alinha com o início do título
                    top.linkTo(parent.top) // Alinha ao topo do item
                    bottom.linkTo(parent.bottom) // Alinha ao fundo do item
                }
                .padding(start = 8.dp) // Espaçamento à esquerda
        )

        Text(
            text = "$${decimalFormat.format(numberInCart * item.Price)}", // Total do item (preço x quantidade)
            fontSize = 18.sp, // Tamanho maior para destaque
            fontWeight = FontWeight.Bold, // Negrito
            modifier = Modifier
                .constrainAs(totalEachItem) {
                    end.linkTo(parent.end) // Alinha ao final do layout
                    bottom.linkTo(pic.bottom) // Alinha ao fundo da imagem
                }
                .padding(8.dp) // Espaçamento interno
        )

        // Layout para o controle de quantidade (+ e -)
        ConstraintLayout(
            modifier = Modifier
                .width(100.dp) // Largura fixa
                .padding(start = 8.dp) // Espaçamento à esquerda
                .constrainAs(quantity) {
                    start.linkTo(titleTxt.start) // Começa após o início do título
                    bottom.linkTo(parent.bottom) // Alinha ao fundo do item
                }
        ) {
            val (plusCartBtn, minusCartBtn, numberItemText) = createRefs() // Referências para botões e quantidade

            Text(
                text = item.numberInCart.toString(), // Quantidade atual
                color = colorResource(R.color.darkPurple), // Cor do texto
                fontSize = 16.sp, // Tamanho da fonte
                fontWeight = FontWeight.Bold, // Negrito
                modifier = Modifier.constrainAs(numberItemText) {
                    end.linkTo(parent.end) // Alinha ao fim do layout
                    start.linkTo(parent.start) // Começa ao início do layout
                    top.linkTo(parent.top) // Alinha ao topo
                    bottom.linkTo(parent.bottom) // Alinha ao fundo
                }
            )

            // Botão para aumentar quantidade
            Box(
                modifier = Modifier
                    .padding(2.dp) // Espaçamento interno
                    .size(28.dp) // Tamanho do botão
                    .constrainAs(plusCartBtn) {
                        end.linkTo(parent.end) // Alinha ao final do layout
                        top.linkTo(parent.top) // Alinha ao topo
                        bottom.linkTo(parent.bottom) // Alinha ao fundo
                    }
                    .clickable {
                        val index = cartItems.indexOf(item) // Encontra índice do item na lista
                        if (index != -1) { // Se encontrou
                            managementCart.plusItem( // Chama função para aumentar quantidade
                                ArrayList(cartItems), // Copia a lista
                                index // Índice do item
                            ) { onItemChange() } // Atualiza a interface
                        }
                    }
            ) {
                Text(
                    text = "+", // Símbolo de aumento
                    color = colorResource(R.color.pink), // Cor do símbolo
                    fontSize = 18.sp, // Tamanho da fonte
                    fontWeight = FontWeight.Bold, // Negrito
                    modifier = Modifier.align(Alignment.Center), // Centralizado
                    textAlign = TextAlign.Center // Alinhamento do texto
                )
            }

            // Botão para diminuir quantidade
            Box(
                modifier = Modifier
                    .padding(2.dp) // Espaçamento interno
                    .size(28.dp) // Tamanho do botão
                    .constrainAs(minusCartBtn) {
                        start.linkTo(parent.start) // Alinha ao início do layout
                        top.linkTo(parent.top) // Alinha ao topo
                        bottom.linkTo(parent.bottom) // Alinha ao fundo
                    }
                    .clickable {
                        val index = cartItems.indexOf(item) // Encontra índice do item
                        if (index != -1) { // Se encontrou
                            managementCart.minusItem( // Chama função para diminuir quantidade
                                ArrayList(cartItems), // Copia a lista
                                index // Índice do item
                            ) { onItemChange() } // Atualiza a interface
                        }
                    }
            ) {
                Text(
                    text = "-", // Símbolo de diminuição
                    color = colorResource(R.color.pink), // Cor do símbolo
                    fontSize = 18.sp, // Tamanho da fonte
                    fontWeight = FontWeight.Bold, // Negrito
                    modifier = Modifier.align(Alignment.Center), // Centralizado
                    textAlign = TextAlign.Center // Alinhamento do texto
                )
            }
        }
    }
}