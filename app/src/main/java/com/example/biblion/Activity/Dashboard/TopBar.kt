package com.example.biblion.Activity.Dashboard

import androidx.compose.foundation.Image // importa componente para exibir imagens
import androidx.compose.foundation.clickable // importa para tornar componentes clicáveis
import androidx.compose.foundation.layout.Column // importa layout coluna
import androidx.compose.foundation.layout.fillMaxWidth // importa para preencher a largura disponível
import androidx.compose.foundation.layout.padding // importa para adicionar espaçamento interno
import androidx.compose.material.Text // importa componente de texto
import androidx.compose.runtime.Composable // marca funções como componentes do Compose
import androidx.compose.ui.Alignment // importa alinhamento de componentes
import androidx.compose.ui.Modifier // permite modificar componentes
import androidx.compose.ui.graphics.Color // permite usar cores
import androidx.compose.ui.res.painterResource // carrega imagens de recursos
import androidx.compose.ui.text.SpanStyle // define estilos para partes do texto
import androidx.compose.ui.text.buildAnnotatedString // constrói textos com estilos variados
import androidx.compose.ui.text.font.FontWeight // define peso da fonte (negrito, etc)
import androidx.compose.ui.text.withStyle // aplica estilos ao texto
import androidx.compose.ui.tooling.preview.Preview // permite visualização no preview do Android Studio
import androidx.compose.ui.unit.dp // unidade de medida para espaçamento
import androidx.compose.ui.unit.sp // unidade de medida para tamanho de fonte
import androidx.constraintlayout.compose.ConstraintLayout // layout que permite posicionar componentes com restrições
import com.example.biblion.R // recursos do projeto, como imagens

@Composable // indica que a função é um componente do Compose
@Preview // permite visualizar o componente na ferramenta de visualização

fun TopBar() { // define a função que cria a barra superior

    ConstraintLayout( // usa layout de restrição para posicionar componentes
        modifier = Modifier // aplica modificadores ao layout
            .padding(top = 48.dp) // espaçamento superior de 48dp
            .padding(horizontal = 16.dp) // espaçamento lateral de 16dp
            .fillMaxWidth() // ocupa toda a largura disponível
    ) {
        val (name, settings, notification) = createRefs() // cria referências para os componentes

        Image(painter = painterResource(R.drawable.settings_icon), // imagem do ícone de configurações
            contentDescription = null, // descrição para acessibilidade (null aqui)
            modifier = Modifier // modificador para a imagem

                .constrainAs(settings){ // posiciona usando a referência "settings"
                    top.linkTo(parent.top) // topo ligado ao topo do layout
                    bottom.linkTo(parent.bottom) // base ligado à base do layout
                    start.linkTo(parent.start) // início ligado ao início do layout
                }
                .clickable {  } // torna clicável (ação vazia aqui)
        )
        Column ( // coluna para textos, alinhados centralmente
            modifier = Modifier // modificador da coluna
                .constrainAs(name){ // posicionado com a referência "name"
                    top.linkTo(parent.top) // topo ao topo do layout
                    start.linkTo(settings.end) // início ao final do ícone de configurações
                    end.linkTo(notification.start) // fim ao início do ícone de sino
                },
            horizontalAlignment = Alignment.CenterHorizontally // centraliza conteúdo horizontalmente
        ){
            Text(text= buildAnnotatedString { // texto com partes estilizadas
                withStyle(style = SpanStyle(color = Color.Black)){ // parte vermelha
                    append("VAMOS") // texto "VAMOS"
                }
                withStyle(style = SpanStyle(color = Color.Black)){ // parte preta
                    append(" LER?") // texto " LER?"
                }
            }
                , fontWeight = FontWeight.Bold, // texto em negrito
                fontSize = 20.sp // tamanho da fonte de 20sp
            )
            Text(text="Loja Online", // texto abaixo do primeiro
                color = Color.DarkGray, // cor cinza escuro
                fontSize = 14.sp // tamanho da fonte de 14sp
            )
        }

        Image(painter = painterResource(R.drawable.bell_icon), // ícone de sino
            contentDescription = null, // descrição nula
            modifier = Modifier // modificador
                .constrainAs(notification){ // posiciona com referência "notification"
                    top.linkTo(parent.top) // topo ao topo do layout
                    bottom.linkTo(parent.bottom) // base à base do layout
                    end.linkTo(parent.end) // fim ao final do layout
                }
                .clickable {  } // torna clicável (ação vazia)
        )
    }
}