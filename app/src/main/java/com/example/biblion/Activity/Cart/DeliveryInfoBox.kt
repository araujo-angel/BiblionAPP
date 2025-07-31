package com.example.biblion.Activity.Cart

import androidx.compose.foundation.Image // Importa componente para exibir imagens
import androidx.compose.foundation.background // Importa componente para fundo de elementos
import androidx.compose.foundation.layout.Column // Importa componente para layout em coluna
import androidx.compose.foundation.layout.Row // Importa componente para layout em linha
import androidx.compose.foundation.layout.Spacer // Importa componente para espaçamento
import androidx.compose.foundation.layout.fillMaxWidth // Importa função para preencher toda a largura
import androidx.compose.foundation.layout.height // Importa função para definir altura
import androidx.compose.foundation.layout.padding // Importa função para padding (espaçamento interno)
import androidx.compose.foundation.layout.width // Importa função para definir largura
import androidx.compose.foundation.shape.RoundedCornerShape // Importa forma arredondada para cantos
import androidx.compose.material3.Button // Importa componente de botão
import androidx.compose.material3.ButtonDefaults // Importa configurações padrão do botão
import androidx.compose.material.Divider // Importa componente para dividir linhas
import androidx.compose.material.DrawerDefaults.shape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Text // Importa componente para texto
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable // Importa anotação para funções composáveis
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment // Importa alinhamento de componentes
import androidx.compose.ui.Modifier // Importa modificador para alterar componentes
import androidx.compose.ui.graphics.Color // Importa classe para cores
import androidx.compose.ui.graphics.painter.Painter // Importa classe para pintar imagens
import androidx.compose.ui.res.colorResource // Importa função para obter cores de recursos
import androidx.compose.ui.res.painterResource // Importa função para obter imagens de recursos
import androidx.compose.ui.text.font.FontWeight // Importa peso da fonte
import androidx.compose.ui.unit.dp // Importa unidade de densidade (dp)
import androidx.compose.ui.unit.sp // Importa unidade de tamanho de fonte (sp)
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biblion.ViewModel.DeliveryViewModel
import com.example.biblion.R // Importa recursos do projeto

@Composable
fun DeliveryInfoBox(deliveryViewModel: DeliveryViewModel = viewModel()) {
    val cep by deliveryViewModel.cep.collectAsState()
    val numero by deliveryViewModel.numero.collectAsState()
    val cepErro by deliveryViewModel.cepErro.collectAsState()
    val numeroErro by deliveryViewModel.numeroErro.collectAsState()
    val endereco by deliveryViewModel.endereco.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(color = colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        // Campo de CEP com botão Buscar
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = cep,
                onValueChange = deliveryViewModel::onCepChange,
                label = { Text("CEP") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = deliveryViewModel::buscarCep,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.pink)
                )
            ) {
                Text(text = "Buscar")
            }
        }

        if (cepErro) {
            Text(
                text = "CEP inválido ou não encontrado.",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Logradouro:")
        TextField(
            value = endereco.logradouro ?: "",
            onValueChange = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Bairro:")
        TextField(
            value = endereco.bairro ?: "",
            onValueChange = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Número:")
        TextField(
            value = numero,
            onValueChange = deliveryViewModel::onNumeroChange,
            modifier = Modifier.fillMaxWidth(),
            label = { },
            isError = numeroErro
        )

        if (numeroErro) {
            Text(
                text = "O número não pode estar vazio.",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        InfoItem(
            title = "Método de pagamento",
            content = "Dinheiro",
            icon = painterResource(R.drawable.credit_card)
        )
    }

    Button(
        onClick = {
            if (deliveryViewModel.validarCampos()) {
                // Prosseguir com o pedido
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.pink)
        ),
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            text = "Fazer pedido",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}


@Composable // Anotação de função composável
fun InfoItem(title: String, content: String, icon: Painter) { // Função que exibe um item de informação
    Column { // Layout em coluna para empilhar elementos
        Text(text = title, fontSize = 14.sp, color = Color.Gray) // Título em cinza e tamanho menor
        Spacer(modifier = Modifier.height(4.dp)) // Espaçamento entre título e conteúdo
        Row(verticalAlignment = Alignment.CenterVertically) { // Linha com alinhamento central
            Image(
                painter = icon, // Ícone passado como parâmetro
                contentDescription = null // Descrição de acessibilidade nula
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espaçamento entre ícone e texto
            Text(
                text = content, fontSize = 18.sp, // Conteúdo maior e em negrito
                fontWeight = FontWeight.Bold
            )
        }
    }
}