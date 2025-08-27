package com.example.biblion.Activity.DetailEachBook

import androidx.compose.foundation.layout.Column // Importa o layout Column para organizar elementos na vertical
import androidx.compose.foundation.layout.padding // Importa a função padding para adicionar espaçamento
import androidx.compose.material3.Text // Importa o componente Text para exibir textos na tela
import androidx.compose.runtime.Composable // Importa a anotação Composable para criar funções que representam partes da interface
import androidx.compose.ui.Modifier // Importa Modifier para modificar elementos visuais
import androidx.compose.ui.res.colorResource // Importa para usar cores definidas nos recursos
import androidx.compose.ui.text.font.FontWeight // Importa para definir o peso da fonte (negrito, normal)
import androidx.compose.ui.unit.dp // Importa unidades de densidade independente para espaçamento
import androidx.compose.ui.unit.sp // Importa unidades de tamanho de fonte em scale-independent pixels
import com.example.biblion.R // Importa os recursos do projeto, incluindo cores

@Composable // Define uma função que pode ser usada na composição da UI
fun DescriptionSection(description:String){ // Função que recebe uma descrição como parâmetro
    Column { // Cria uma coluna para organizar elementos na vertical
        Text( // Cria uma caixa de texto para o título "Detalhes"
            text="Detalhes", // Texto exibido como título
            fontSize = 18.sp, // Tamanho da fonte em scale-independent pixels
            fontWeight = FontWeight.Bold, // Deixa o texto em negrito
            color = colorResource(R.color.black), // Cor do texto usando recurso de cor
            modifier = Modifier.padding(horizontal = 16.dp) // Adiciona espaçamento horizontal de 16dp ao redor
        )
        Text( // Cria uma caixa de texto para a descrição do alimento
            text = description, // Texto vindo do parâmetro da função
            fontSize = 16.sp, // Tamanho da fonte
            color= colorResource(R.color.black), // Cor do texto usando recurso de cor
            modifier = Modifier.padding(16.dp) // Espaçamento de 16dp ao redor do texto
        )
    }
}