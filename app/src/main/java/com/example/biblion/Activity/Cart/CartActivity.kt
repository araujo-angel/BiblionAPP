package com.example.biblion.Activity.Cart

import android.os.Bundle // Importa a classe Bundle para manipular dados do ciclo de vida
import androidx.activity.compose.setContent // Permite definir o conteúdo da activity com Compose
import androidx.appcompat.app.AppCompatActivity // Classe base para atividades compatíveis com ActionBar
import androidx.compose.foundation.Image // Para exibir imagens na interface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // Para tornar componentes clicáveis
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth // Para preencher toda a largura disponível
import androidx.compose.foundation.layout.padding // Para adicionar espaçamento interno
import androidx.compose.foundation.lazy.LazyColumn // Lista preguiçosa que carrega itens sob demanda
import androidx.compose.foundation.lazy.items // Para iterar sobre itens na LazyColumn
import androidx.compose.material3.Text // Componente de texto
import androidx.compose.runtime.Composable // Para marcar funções como componentes Compose
import androidx.compose.runtime.MutableState // Para criar estados mutáveis
import androidx.compose.runtime.mutableStateListOf // Lista mutável que é observada pelo Compose
import androidx.compose.runtime.mutableStateOf // Estado mutável simples
import androidx.compose.runtime.remember // Para lembrar valores ao recompor
import androidx.compose.ui.Modifier // Para modificar componentes de UI
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Para obter o contexto atual
import androidx.compose.ui.res.colorResource // Para usar cores definidas nos recursos
import androidx.compose.ui.res.painterResource // Para carregar recursos de imagem
import androidx.compose.ui.text.font.FontWeight // Para definir peso da fonte
import androidx.compose.ui.text.style.TextAlign // Para alinhar o texto
import androidx.compose.ui.unit.dp // Unidade de medida para espaçamento
import androidx.compose.ui.unit.sp // Unidade de medida para tamanho de fonte
import androidx.constraintlayout.compose.ConstraintLayout // Para layouts com restrições
import com.example.biblion.Domain.BookModel // Modelo de dados de alimentos
import com.example.biblion.Helper.ManagementCart
//import com.example.biblion.Helper.ManagementCart // Classe que gerencia o carrinho
import com.example.biblion.R // Recursos do projeto

class CartActivity : AppCompatActivity() { // Define a atividade do carrinho
    override fun onCreate(savedInstanceState: Bundle?) { // Método chamado na criação da atividade
        super.onCreate(savedInstanceState) // Chama o método da classe pai
        setContent { // Define o conteúdo usando Compose
            CartScreen(
                ManagementCart(this), // Cria uma instância de gerenciamento do carrinho
                onBackClick = { finish() }) // Fecha a atividade ao clicar no botão voltar
        }
    }
}

@Composable // Marca a função como componente Compose
fun CartScreen(
    managementCart: ManagementCart = ManagementCart(LocalContext.current), // Gerenciador do carrinho, padrão com contexto atual
    onBackClick: () -> Unit // Função para tratar clique no botão voltar
) {
    val cartItems = remember { // Lembra a lista de itens do carrinho
        mutableStateListOf<BookModel>().apply { addAll(managementCart.getListCart()) } // Inicializa com itens do carrinho
    }
    val tax = remember { mutableStateOf(0.0) } // Estado para valor de imposto (taxa)

    calculatorCart(managementCart, tax) // Calcula o valor do imposto com base no carrinho

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    )
    LazyColumn( // Lista que carrega itens sob demanda
        modifier = Modifier
            .fillMaxWidth() // Ocupa toda a largura disponível
            .padding(16.dp) // Espaçamento interno de 16dp
    ) {
        item { // Primeiro item: cabeçalho com botão voltar
            ConstraintLayout(modifier = Modifier.padding(top = 36.dp)) { // Layout com restrições, com espaçamento superior
                val (backBtn, cartTxt) = createRefs() // Referências para botões e textos
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(cartTxt) { centerTo(parent) }, // Centraliza o texto no layout
                    text = "Seu carrinho", // Texto do cabeçalho
                    textAlign = TextAlign.Center, // Centraliza o texto
                    fontWeight = FontWeight.Bold, // Negrito
                    fontSize = 25.sp, // Tamanho da fonte
                    color = Color.White
                )
                Image(
                    painter = painterResource(R.drawable.back_grey), // Imagem de seta de voltar
                    contentDescription = null, // Sem descrição de acessibilidade
                    modifier = Modifier
                        .constrainAs(backBtn) {
                            top.linkTo(parent.top) // Alinha ao topo
                            bottom.linkTo(parent.bottom) // Alinha à parte inferior
                            start.linkTo(parent.start) // Alinha ao início (esquerda)
                        }
                        .clickable { onBackClick() } // Torna clicável para voltar
                )
            }
        }
        if (cartItems.isEmpty()) { // Se o carrinho estiver vazio
            item { // Exibe mensagem de carrinho vazio
                Text(
                    text = "O carrinho está vazio", // Texto exibido
                    modifier = Modifier
                        .padding(top = 16.dp) // Espaçamento superior
                        .fillMaxWidth(), // Preenche a largura
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        } else { // Se houver itens no carrinho
            items(cartItems, key = { it.Title }) { item -> // Para cada item do carrinho
                CartItem( // Componente para exibir item do carrinho
                    cartItems = cartItems, // Lista de itens
                    item = item, // Item atual
                    managementCart = managementCart, // Gerenciador do carrinho
                    onItemChange = { // Quando o item mudar
                        calculatorCart(managementCart, tax) // Recalcula o imposto
                        cartItems.clear() // Limpa a lista atual
                        cartItems.addAll(managementCart.getListCart()) // Atualiza com a nova lista do carrinho
                    }
                )
            }

            item { // Título do resumo do pedido
                Text(
                    text = "Resumo do pedido", // Texto do título
                    color = Color.Black, // Cor personalizada
                    fontSize = 18.sp, // Tamanho da fonte
                    fontWeight = FontWeight.Bold, // Negrito
                    modifier = Modifier.padding(top = 16.dp) // Espaçamento superior
                )
            }
            item { // Componente de resumo do carrinho
                CartSummary(
                    itemTotal = managementCart.getTotalFee(), // Valor total dos itens
                    tax = tax.value, // Valor do imposto
                    delivery = 10.0 // Valor da entrega
                )
            }
            item { // Título de informações adicionais
                Text(
                    text = "Endereço de Entrega", // Texto do título
                    color = colorResource(R.color.black), // Cor
                    fontSize = 18.sp, // Tamanho da fonte
                    fontWeight = FontWeight.Bold, // Negrito
                    modifier = Modifier.padding(top = 16.dp) // Espaçamento superior
                )
            }
            item { // Informação de entrega
                DeliveryInfoBox()
            }
        }
    }
}

// Função para calcular o valor do imposto com base no total do carrinho
fun calculatorCart(managementCart: ManagementCart, tax: MutableState<Double>) {
    val percentTax = 0.02 // Percentual de imposto (2%)
    tax.value = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100.0 // Calcula e arredonda o imposto para duas casas decimais
}