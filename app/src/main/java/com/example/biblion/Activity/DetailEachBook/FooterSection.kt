package com.example.biblion.Activity.DetailEachBook

import android.icu.text.DecimalFormat // importa a classe para formatar números decimais
import androidx.compose.foundation.background // permite colocar cor de fundo
import androidx.compose.foundation.layout.Column // disposição em coluna
import androidx.compose.foundation.layout.Spacer // espaço entre elementos
import androidx.compose.foundation.layout.fillMaxWidth // preencher toda a largura disponível
import androidx.compose.foundation.layout.height // definir altura
import androidx.compose.foundation.layout.padding // adicionar espaçamento interno
import androidx.compose.foundation.layout.size // definir tamanho de componentes
import androidx.compose.foundation.layout.width // definir largura
import androidx.compose.foundation.shape.RoundedCornerShape // forma arredondada
import androidx.compose.material.Icon // ícone gráfico
import androidx.compose.material3.Button // botão de material3
import androidx.compose.material3.ButtonDefaults // configurações padrão do botão
import androidx.compose.material3.Text // texto
import androidx.compose.runtime.Composable // anotação para funções que constroem UI
import androidx.compose.ui.Modifier // modifica componentes
import androidx.compose.ui.graphics.Color // cores
import androidx.compose.ui.res.colorResource // recursos de cores
import androidx.compose.ui.res.painterResource // recursos de imagens
import androidx.compose.ui.text.font.FontWeight // peso da fonte
import androidx.compose.ui.unit.dp // unidades de medida (density-independent pixels)
import androidx.compose.ui.unit.sp // unidades de fonte (scale-independent pixels)
import androidx.constraintlayout.compose.ConstraintLayout // layout com restrições
import com.example.biblion.R // recursos do projeto

@Composable
fun FooterSection(onAddToCartClick: () -> Unit, totalPrice: Double, modifier: Modifier = Modifier) {
    // Componente que exibe a seção do rodapé com botões e preço

    ConstraintLayout(
        modifier = modifier
            .height(75.dp) // altura fixa de 75dp
            .fillMaxWidth() // ocupa toda a largura disponível
            .background(color = colorResource(R.color.grey)) // fundo cinza
            .padding(horizontal = 16.dp) // espaçamento horizontal de 16dp
    ) {
        val (orderBtn, price) = createRefs() // referências para posicionar elementos

        Button(
            onClick = onAddToCartClick, // ação ao clicar no botão
            shape = RoundedCornerShape(100.dp), // botão com cantos arredondados
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.pink) // cor de fundo laranja
            ),
            modifier = Modifier
                .width(170.dp) // largura de 140dp
                .height(50.dp) // altura de 50dp
                .constrainAs(orderBtn) { // posiciona usando a referência orderBtn
                    top.linkTo(parent.top) // alinhado ao topo do pai
                    bottom.linkTo(parent.bottom) // alinhado à parte inferior do pai
                    end.linkTo(parent.end) // alinhado à direita do pai
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.cart), // ícone de carrinho
                contentDescription = null, // sem descrição de acessibilidade
                tint = Color.White, // cor branca para o ícone
                modifier = Modifier.size(20.dp) // tamanho do ícone
            )
            Spacer(modifier = Modifier.width(16.dp)) // espaço de 16dp entre ícone e texto
            Text("Comprar", fontSize = 20.sp, color = Color.White) // texto "Ordem" em branco
        }

        Column(
            modifier = Modifier
                .width(140.dp) // largura de 140dp
                .height(50.dp) // altura de 50dp
                .constrainAs(price) { // posição usando a referência price
                    top.linkTo(parent.top) // alinhado ao topo
                    bottom.linkTo(parent.bottom) // alinhado à parte inferior
                    start.linkTo(parent.start) // alinhado à esquerda
                }
        ) {
            Text("Total Preço", fontSize = 18.sp, color = colorResource(R.color.black)) // label de preço
            val decimalFormat = DecimalFormat("#.00") // formata número com duas casas decimais
            Text(
                "$${decimalFormat.format(totalPrice)}", // exibe o preço formatado com símbolo de dólar
                fontSize = 18.sp, // tamanho da fonte
                modifier = Modifier.padding(top = 8.dp), // espaçamento superior de 8dp
                fontWeight = FontWeight.Bold, // texto em negrito
                color = colorResource(R.color.black) // cor roxa escura
            )
        }
    }
}