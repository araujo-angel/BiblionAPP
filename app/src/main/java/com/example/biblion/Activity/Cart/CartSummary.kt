package com.example.biblion.Activity.Cart

import androidx.compose.foundation.background // importa para usar fundos coloridos
import androidx.compose.foundation.layout.Box // importa a caixa para agrupar elementos
import androidx.compose.foundation.layout.Column // importa para alinhar elementos verticalmente
import androidx.compose.foundation.layout.Row // importa para alinhar elementos horizontalmente
import androidx.compose.foundation.layout.fillMaxWidth // importa para fazer o elemento preencher toda a largura
import androidx.compose.foundation.layout.height // importa para definir altura
import androidx.compose.foundation.layout.padding // importa para adicionar espaçamento interno
import androidx.compose.foundation.shape.RoundedCornerShape // importa para cantos arredondados
import androidx.compose.material3.Text // importa para exibir textos
import androidx.compose.runtime.Composable // importa para marcar funções como componentes de UI
import androidx.compose.ui.Modifier // importa para modificar atributos dos componentes
import androidx.compose.ui.graphics.Color // importa para usar cores
import androidx.compose.ui.res.colorResource // importa para usar cores definidas nos recursos
import androidx.compose.ui.text.font.FontWeight // importa para definir peso da fonte
import androidx.compose.ui.unit.dp // importa para trabalhar com unidades de medida (density-independent pixels)
import com.example.biblion.R // importa recursos do projeto (como cores)
import java.text.DecimalFormat // importa para formatar números decimais

@Composable // indica que a função é um componente de UI reutilizável
fun CartSummary(itemTotal: Double, tax: Double, delivery: Double) { // função que exibe o resumo do carrinho com valores passados como parâmetros
    val total = itemTotal + tax + delivery // calcula o valor total somando itens, imposto e entrega
    val decimalFormat = DecimalFormat("#.000") // cria formato para mostrar números com três casas decimais
    Column( // empilha elementos verticalmente
        modifier = Modifier
            .fillMaxWidth() // ocupa toda a largura disponível
            .padding(top = 8.dp) // espaço acima de 8dp
            .background(colorResource(R.color.grey), shape = RoundedCornerShape(10.dp)) // fundo cinza com cantos arredondados
            .padding(8.dp) // espaço interno de 8dp
    ) {
        Row( // linha para mostrar subtotal
            modifier = Modifier
                .fillMaxWidth() // ocupa toda a largura
                .padding(top = 8.dp) // espaço acima de 8dp
        ) {
            Text(
                text = "Subtotal:", // texto estático
                Modifier.weight(1f), // ocupa peso para alinhar com o valor
                color = colorResource(R.color.black) // cor do texto
            )
            Text(text = "${decimalFormat.format(itemTotal)}") // mostra o subtotal formatado com 3 casas decimais
        }

        Row( // linha para mostrar valor de entrega
            modifier = Modifier
                .fillMaxWidth() // ocupa toda a largura
                .padding(top = 16.dp) // espaço acima de 16dp
        ) {
            Text(
                text = "Entrega:", // texto estático
                Modifier.weight(1f), // ocupa peso para alinhar
                color = colorResource(R.color.black) // cor do texto
            )
            Text(text = "$delivery") // mostra o valor de entrega
        }

        Row( // linha para mostrar taxas
            modifier = Modifier
                .fillMaxWidth() // ocupa toda a largura
                .padding(top = 16.dp) // espaço acima de 16dp
        ) {
            Text(
                text = "Total Taxas:", // texto estático
                Modifier.weight(1f), // ocupa peso para alinhar
                color = colorResource(R.color.black) // cor do texto
            )
            Text(text = "$tax") // mostra o valor do imposto/taxas
        }

        Box( // caixa para criar uma linha horizontal de separação
            modifier = Modifier
                .padding(top=16.dp) // espaço acima de 16dp
                .height(1.dp) // altura de 1dp para a linha
                .fillMaxWidth() // ocupa toda a largura
                .background(Color.Gray) // fundo cinza para a linha
        )

        Row( // linha para mostrar o total final
            modifier = Modifier
                .fillMaxWidth() // ocupa toda a largura
                .padding(top = 16.dp) // espaço acima de 16dp
        ) {
            Text(
                text = "Total:", // texto estático
                Modifier.weight(1f), // ocupa peso para alinhar
                fontWeight = FontWeight.Bold, // negrito
                color = colorResource(R.color.darkPurple) // cor do texto
            )
            Text(
                text = "$${decimalFormat.format(total)}", // mostra o total formatado com 3 casas decimais, em negrito
                fontWeight=FontWeight.Bold
            )
        }
    }

} // fim da função e componente