package com.example.biblion.Splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.biblion.R
import androidx.compose.ui.Modifier // Permite modificar componentes visuais
import androidx.compose.foundation.Image // Componente para exibir imagens
import androidx.compose.foundation.background // Permite definir a cor de fundo
import androidx.compose.foundation.layout.Column // Layout em coluna
import androidx.compose.foundation.layout.fillMaxSize // Faz o componente preencher toda a tela
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color // Para usar cores
import androidx.compose.ui.layout.ContentScale // Controla como a imagem é ajustada
import androidx.compose.ui.res.colorResource // Carrega cores de recursos
import androidx.compose.ui.res.painterResource // Carrega imagens de recursos
import androidx.compose.ui.res.stringResource // Carrega strings de recursos
import androidx.compose.ui.text.SpanStyle // Estilo para partes do texto
import androidx.compose.ui.text.buildAnnotatedString // Para criar textos com estilos diferentes
import androidx.compose.ui.text.font.FontWeight // Para definir peso da fonte
import androidx.compose.ui.text.withStyle // Aplica estilo a partes do texto
import androidx.compose.ui.unit.dp // Unidade de medida para espaçamento
import androidx.compose.ui.unit.sp // Unidade de medida para tamanhos de fonte
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.biblion.Activity.Login.LoginActivity

class SplashActivity : AppCompatActivity() { // Classe que representa a tela de splash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tinyDB = com.example.biblion.Helper.TinyDB(this)

        setContent {
            SplashScreen(
                onGetStartedClick = {
                    val username = tinyDB.getString("username")

                    if (username.isNotEmpty()) {
                        // Já está logado, vai para a tela principal
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        // Não está logado, vai para tela de login
                        startActivity(Intent(this, com.example.biblion.Activity.Login.LoginActivity::class.java))
                    }

                    finish() // Fecha a splash
                }
            )
        }
    }
}

@Composable
@Preview
fun SplashScreen(onGetStartedClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.blackPink)),

    ) {
        ConstraintLayout(
            modifier = Modifier.padding(top = 48.dp)
        ) {
            val (backgroundImg, logiImg) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.intro_pic),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(backgroundImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .fillMaxWidth()
            )
            Image(
                painter = painterResource(R.drawable.pilhadelivros),
                contentDescription = null,
                modifier = Modifier.run {
                    constrainAs(logiImg) {
                        top.linkTo(backgroundImg.top)
                        bottom.linkTo(backgroundImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                },
                contentScale = ContentScale.Fit

            )
        }
        val styledText= buildAnnotatedString { // Cria um texto com estilos diferentes
            append("Bem-vindo ao App ") // Texto padrão
            withStyle(style = SpanStyle(color = colorResource(R.color.pink))){ // Estilo para a próxima parte
                append("Biblion  \n") // Texto com cor diferente e quebra de linha
            }
            append("\nAproveite nossas promoções.") // Continuação do texto
        }
        androidx.compose.material.Text( // Componente de texto
            text = styledText, // Texto com estilos
            fontSize = 27.sp, // Tamanho da fonte
            fontWeight = FontWeight.Bold, // Negrito
            color = Color.White, // Cor branca
            modifier = Modifier
                .padding(top = 32.dp) // Espaçamento superior
                .padding(horizontal = 16.dp) // Espaçamento lateral
                .align(androidx.compose.ui.Alignment.CenterHorizontally)
        )

        androidx.compose.material.Text( // Outro texto
            text = stringResource(R.string.splashSubtitle), // Texto da string de recursos
            fontSize = 16.sp, // Tamanho menor
            color = Color.White, // Cor branca
            modifier = Modifier
                .padding(16.dp) // Espaçamento ao redor
                .align(androidx.compose.ui.Alignment.CenterHorizontally)
        )

        GetStartedButton( // Botão Iniciar
            onClick = onGetStartedClick, // Ação ao clicar
            modifier = Modifier
                .padding(top = 16.dp) // Espaçamento acima do botão
        )
    }
}