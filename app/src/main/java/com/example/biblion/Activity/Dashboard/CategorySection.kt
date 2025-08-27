package com.example.biblion.Activity.Dashboard

import android.content.Intent // Importa a classe Intent para abrir novas telas
import androidx.compose.foundation.background // Para definir fundo em componentes
import androidx.compose.foundation.clickable // Para tornar componentes clicáveis
import androidx.compose.foundation.layout.Arrangement // Para organizar elementos em linhas e colunas
import androidx.compose.foundation.layout.Box // Container que pode conter outros componentes
import androidx.compose.foundation.layout.Column // Organiza componentes em coluna
import androidx.compose.foundation.layout.Row // Organiza componentes em linha
import androidx.compose.foundation.layout.Spacer // Espaçamento entre componentes
import androidx.compose.foundation.layout.fillMaxWidth // Faz o componente ocupar toda a largura disponível
import androidx.compose.foundation.layout.height // Define altura de componentes
import androidx.compose.foundation.layout.padding // Adiciona espaçamento interno
import androidx.compose.foundation.layout.size // Define tamanho de componentes
import androidx.compose.foundation.shape.RoundedCornerShape // Forma arredondada para fundos e bordas
import androidx.compose.material.CircularProgressIndicator // Indicador de carregamento circular
import androidx.compose.material3.Text // Componente de texto
import androidx.compose.runtime.Composable // Marca funções como componentes do Compose
import androidx.compose.runtime.snapshots.SnapshotStateList // Lista que pode mudar de estado
import androidx.compose.ui.Alignment // Alinhamento dos componentes
import androidx.compose.ui.Modifier // Permite modificar componentes (tamanho, comportamento, etc)
import androidx.compose.ui.platform.LocalContext // Contexto atual do Android
import androidx.compose.ui.res.colorResource // Carrega cores dos recursos
import androidx.compose.ui.text.font.FontWeight // Define peso da fonte (negrito, normal)
import androidx.compose.ui.tooling.preview.Preview // Para visualização prévia no Android Studio
import androidx.compose.ui.unit.dp // Unidade de medida (density-independent pixels)
import androidx.compose.ui.unit.sp // Unidade de medida para tamanhos de fonte
import androidx.core.content.ContextCompat.startActivity // Método para iniciar atividade
import coil.compose.AsyncImage // Carregador de imagens assíncrono
import com.example.biblion.Domain.CategoryModel // Modelo de Categoria
import com.example.biblion.Activity.ItemsList.ItemsListActivity // Tela de lista de itens
import com.example.biblion.R // Recursos do projeto
import kotlin.jvm.java

// Função que exibe uma seção de categorias
@Composable
fun CategorySection(
    categories: SnapshotStateList<CategoryModel>,
    showCategoryLoading: Boolean
) { // Define a função que mostra a seção de categorias
    Text(
        text = "Escolha a categoria", // Texto do título
        fontSize = 18.sp, // Tamanho da fonte
        fontWeight = FontWeight.Bold, // Texto em negrito
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ) // Espaçamento ao redor do texto
    )

    if (showCategoryLoading) { // Se estiver carregando categorias
        Box(
            modifier = Modifier
                .fillMaxWidth() // Ocupa toda a largura
                .height(50.dp), // Altura de 50dp
            contentAlignment = Alignment.Center // Centraliza o conteúdo
        ) {
            CircularProgressIndicator() // Mostra o indicador de carregamento
        }
    } else { // Se não estiver carregando
        val rows =
            categories.chunked(3) // Divide as categorias em grupos de 3 para exibição em linhas
        val context = LocalContext.current // Pega o contexto atual do Android

        Column(
            modifier = Modifier
                .fillMaxWidth() // Ocupa toda a largura
                .padding(8.dp) // Espaçamento interno
        ) {
            rows.forEach { row -> // Para cada grupo de categorias (linha)
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // Linha ocupa toda a largura
                        .padding(vertical = 8.dp), // Espaçamento vertical
                    horizontalArrangement = Arrangement.SpaceBetween // Distribui espaço entre os itens
                ) {
                    row.forEachIndexed { index, categoryModel -> // Para cada categoria na linha
                        CategoryItem(
                            category = categoryModel, // Passa a categoria
                            modifier = Modifier
                                .weight(1f) // Cada item ocupa o mesmo espaço
                                .padding(horizontal = 8.dp), // Espaçamento horizontal
                            onItemClick = { // Ação ao clicar na categoria
                                val intent = Intent(
                                    context,
                                    ItemsListActivity::class.java
                                ).apply { // Cria a intent para abrir a lista de itens
                                    putExtra(
                                        "id",
                                        categoryModel.CategoryId.toString()
                                    ) // Passa o ID da categoria
                                    putExtra(
                                        "title",
                                        categoryModel.CategoryName
                                    ) // Passa o nome da categoria
                                }
                                startActivity(context, intent, null) // Inicia a nova atividade
                            }
                        )
                    }
                    if (row.size < 3) { // Se a linha tiver menos de 3 categorias
                        repeat(3 - row.size) { // Preenche o espaço restante
                            Spacer(modifier = Modifier.weight(1f)) // Espaço vazio proporcional
                        }
                    }
                }
            }
        }
    }
}

// Componente que representa um item de categoria
@Composable
fun CategoryItem(
    category: CategoryModel,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) { // Parâmetros do item
    Column(
        modifier = modifier
            .fillMaxWidth() // O componente ocupa toda a largura disponível
            .background(
                color = colorResource(R.color.lightPink), // Cor de fundo
                shape = RoundedCornerShape(13.dp) // Cantos arredondados
            )
            .clickable(onClick = onItemClick) // Torna clicável
            .padding(1.dp), // Espaçamento interno
        horizontalAlignment = Alignment.CenterHorizontally // Centraliza o conteúdo horizontalmente
    ) {
        AsyncImage(
            model = category.image, // Caminho da imagem da categoria
            contentDescription = null, // Descrição da imagem (não fornecida)
            modifier = Modifier.size(120.dp) // Tamanho da imagem
        )
        Text(
            text = category.CategoryName, // Nome da categoria
            color = colorResource(R.color.black), // Cor do texto
            fontSize = 14.sp, // Tamanho da fonte
            fontWeight = FontWeight.Bold, // Texto em negrito
            modifier = Modifier.padding(top = 8.dp) // Espaçamento acima do texto
        )
    }
}