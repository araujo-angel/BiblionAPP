package com.example.biblion.Activity.DetailEachBook

import android.annotation.SuppressLint // importa anotação para suppressão de avisos
import androidx.compose.foundation.background // importa função para fundo de componentes
import androidx.compose.foundation.clickable // importa função para tornar componentes clicáveis
import androidx.compose.foundation.layout.Box // importa componente para empilhar elementos
import androidx.compose.foundation.layout.padding // importa função para espaçamento interno
import androidx.compose.foundation.layout.size // importa função para definir tamanho
import androidx.compose.foundation.layout.width // importa função para definir largura
import androidx.compose.foundation.layout.wrapContentSize // importa para ajustar o tamanho ao conteúdo
import androidx.compose.foundation.shape.CircleShape // importa forma circular
import androidx.compose.foundation.shape.RoundedCornerShape // importa forma arredondada
import androidx.compose.material3.Text // importa componente de texto
import androidx.compose.runtime.Composable // importa anotação para funções composables
import androidx.compose.ui.Alignment // importa para alinhamento
import androidx.compose.ui.Modifier // importa para modificar componentes
import androidx.compose.ui.graphics.Color // importa para cores
import androidx.compose.ui.res.colorResource // importa para usar cores de recursos
import androidx.compose.ui.text.font.FontWeight // importa para peso da fonte
import androidx.compose.ui.text.style.TextAlign // importa para alinhamento do texto
import androidx.compose.ui.unit.dp // importa unidade de medida dp
import androidx.compose.ui.unit.sp // importa unidade de tamanho de fonte
import androidx.constraintlayout.compose.ConstraintLayout // importa layout de restrições
import com.example.biblion.Domain.BookModel // importa o modelo de dado BookModel
import com.example.biblion.R // importa recursos do projeto

@SuppressLint("SuspiciousIndentation") // suprime aviso de indentação suspeita
@Composable // indica que a função é um componente de UI
fun NumberRow(
    item: BookModel, // item de comida com preço
    numberInCart: Int, // quantidade de itens no carrinho
    onIncrement: () -> Unit, // ação ao aumentar quantidade
    onDecrement: () -> Unit, // ação ao diminuir quantidade
    modifier: Modifier = Modifier // modificador padrão
) {
    ConstraintLayout(modifier = modifier) { // layout com restrições, usando o modificador passado
        val (price, buttons) = createRefs() // referências para posicionar elementos: preço e botões

        // layout para os botões de quantidade e o número
        ConstraintLayout(
            modifier = Modifier
                .width(100.dp) // largura fixa de 100dp
                .padding(start = 8.dp) // espaçamento à esquerda
                .background(
                    shape = RoundedCornerShape(100.dp), // cantos arredondados bem grandes
                    color = colorResource(R.color.pink) // cor de fundo laranja
                )
                .constrainAs(buttons) { // define como o layout se posiciona
                    end.linkTo(parent.end) // à direita do pai
                    start.linkTo(parent.start) // à esquerda do pai
                    top.linkTo(parent.top) // no topo do pai
                    bottom.linkTo(parent.bottom) // na parte de baixo do pai
                }
        ) {
            val (plusCartBtn, minusCartBtn, numberItemText) = createRefs() // referências para os botões e o texto do número

            // Texto que mostra a quantidade de itens no carrinho
            Text(
                text = "$numberInCart", // mostra a quantidade
                color = colorResource(R.color.black), // cor roxa escura
                fontSize = 16.sp, // tamanho da fonte
                fontWeight = FontWeight.Bold, // negrito
                modifier = Modifier
                    .size(28.dp) // tamanho do container
                    .background(color = Color.White, shape = CircleShape) // fundo branco em círculo
                    .wrapContentSize(Alignment.Center) // centraliza o conteúdo
                    .constrainAs(numberItemText) { // restrições de posição
                        top.linkTo(parent.top) // no topo
                        bottom.linkTo(parent.bottom) // na parte de baixo
                        start.linkTo(parent.start) // à esquerda
                        end.linkTo(parent.end) // à direita
                    }
            )

            // Botão de incremento "+"
            Box(
                modifier = Modifier
                    .padding(2.dp) // espaçamento interno
                    .size(28.dp) // tamanho do botão
                    .constrainAs(plusCartBtn) { // restrições de posição
                        end.linkTo(parent.end) // à direita
                        top.linkTo(parent.top) // no topo
                        bottom.linkTo(parent.bottom) // na parte de baixo
                    }
                    .clickable { onIncrement() } // ação ao clicar: aumenta quantidade
            ) {
                Text(
                    text = "+", // símbolo de mais
                    color = Color.White, // cor do texto
                    fontSize = 18.sp, // tamanho da fonte
                    fontWeight = FontWeight.Bold, // negrito
                    modifier = Modifier.align(Alignment.Center), // centraliza no box
                    textAlign = TextAlign.Center // alinhamento do texto
                )
            }

            // Botão de decremento "-"
            Box(
                modifier = Modifier
                    .padding(2.dp) // espaçamento interno
                    .size(28.dp) // tamanho do botão
                    .constrainAs(minusCartBtn) { // restrições de posição
                        start.linkTo(parent.start) // à esquerda
                        top.linkTo(parent.top) // no topo
                        bottom.linkTo(parent.bottom) // na parte inferior
                    }
                    .clickable { onDecrement() } // ação ao clicar: diminui quantidade
            ) {
                Text(
                    text = "-", // símbolo de menos
                    color = Color.White, // cor do texto
                    fontSize = 18.sp, // tamanho da fonte
                    fontWeight = FontWeight.Bold, // negrito
                    modifier = Modifier.align(Alignment.Center), // centraliza
                    textAlign = TextAlign.Center // alinhamento do texto
                )
            }
        }

        // Texto que mostra o preço do item
        Text(
            text = "$${item.Price}", // exibe o preço com prefixo "$$"
            fontSize = 20.sp, // tamanho da fonte
            fontWeight = FontWeight.Bold, // negrito
            color = colorResource(R.color.darkPink), // cor roxa escura
            modifier = Modifier
                .padding(horizontal = 16.dp) // espaçamento horizontal
                .constrainAs(price) { // restrições de posicionamento
                    end.linkTo(buttons.start) // à esquerda do layout de botões
                    top.linkTo(parent.top) // no topo do pai
                    bottom.linkTo(parent.bottom) // na parte de baixo
                }
        )
    }
}