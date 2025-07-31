package com.example.biblion.Activity.Dashboard

import android.content.Intent // Importa para criar intents para trocar de activity
import android.widget.Toast // Importa para mostrar mensagens na tela
import androidx.compose.foundation.layout.padding // Importa para adicionar espaçamento ao redor dos componentes
import androidx.compose.foundation.layout.size // Importa para definir o tamanho dos componentes
import androidx.compose.material.BottomAppBar // Importa o componente barra inferior
import androidx.compose.material.BottomNavigationItem // Importa item de navegação na barra inferior
import androidx.compose.material3.Icon // Importa o componente ícone
import androidx.compose.runtime.Composable // Importa para marcar funções como composable (UI)
import androidx.compose.runtime.getValue // Importa para obter valores de estados
import androidx.compose.runtime.mutableStateOf // Importa para criar estados mutáveis
import androidx.compose.runtime.remember // Importa para lembrar valores durante recomposições
import androidx.compose.runtime.setValue // Importa para definir valores de estados
import androidx.compose.ui.Modifier // Importa para modificar componentes (como tamanho, espaçamento)
import androidx.compose.ui.graphics.painter.Painter // Importa para usar imagens/painters
import androidx.compose.ui.platform.LocalContext // Importa para acessar o contexto atual
import androidx.compose.ui.res.colorResource // Importa para usar cores dos recursos
import androidx.compose.ui.res.painterResource // Importa para usar imagens dos recursos
import androidx.compose.ui.tooling.preview.Preview // Importa para visualização no preview do Android Studio
import androidx.compose.ui.unit.dp // Importa para usar unidades de medida como dp
import com.example.biblion.Activity.Cart.CartActivity
//import com.example.biblion.Activity.Cart.CartActivity // Importa a activity do carrinho
import com.example.biblion.R // Importa recursos do projeto
import com.example.biblion.Activity.Dashboard.FavoritesActivity

@Composable // Indica que a função é uma composição de UI
@Preview // Permite visualizar a composição no Android Studio

fun MyBottomBar() {

    val bottomMenuItemsList = prepareBottomMenu() // Cria a lista de itens do menu inferior
    val context = LocalContext.current // Pega o contexto atual da aplicação
    var selectedItem by remember { mutableStateOf("Home") } // Mantém o estado do item selecionado, começando por "Home"

    BottomAppBar(
        backgroundColor = colorResource(R.color.grey), // Define a cor de fundo da barra
        elevation = 3.dp // Define a sombra da barra
    ) {
        bottomMenuItemsList.forEach { bottomMenuItem -> // Para cada item do menu
            BottomNavigationItem(


                selected = (selectedItem == bottomMenuItem.label), // Verifica se o item está selecionado
                onClick = { // Quando o item for clicado

                    selectedItem = bottomMenuItem.label // Atualiza o item selecionado

                    if (bottomMenuItem.label == "Carrinho") {
                        context.startActivity(Intent(context, CartActivity::class.java))
                    } else if (bottomMenuItem.label == "Favoritos") {
                        context.startActivity(Intent(context, FavoritesActivity::class.java))
                    } else {
                        Toast.makeText(context, bottomMenuItem.label, Toast.LENGTH_SHORT).show()
                    }
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon, // Usa o ícone do item
                        contentDescription = null, // Descrição do conteúdo (não fornecida)
                        modifier = Modifier
                            .padding(top = 8.dp) // Adiciona espaçamento acima do ícone
                            .size(20.dp) // Define o tamanho do ícone
                    )
                }
            )

        }
    }
}

data class BottomMenuItem(
    val label: String, val icon: Painter // Classe que representa um item do menu com label e ícone
)


@Composable // Indica que a função é uma composição de UI
fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>() // Cria uma lista mutável de itens do menu

    bottomMenuItemList.add(BottomMenuItem(label = "Home", icon = painterResource(R.drawable.btn_1))) // Adiciona "Home" com seu ícone
    bottomMenuItemList.add(BottomMenuItem(label = "Carrinho", icon = painterResource(R.drawable.btn_2))) // Adiciona "Carrinho" com seu ícone
    bottomMenuItemList.add(BottomMenuItem(label = "Favoritos",icon = painterResource(R.drawable.btn_3)))
    bottomMenuItemList.add(BottomMenuItem(label = "Lista",icon = painterResource(R.drawable.btn_4)))
    bottomMenuItemList.add(BottomMenuItem(label = "Perfil",icon = painterResource(R.drawable.btn_5)))

    return bottomMenuItemList // Retorna a lista de itens do menu
}