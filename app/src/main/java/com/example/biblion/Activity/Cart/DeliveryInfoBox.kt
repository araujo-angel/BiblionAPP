package com.example.biblion.Activity.Cart

import androidx.compose.foundation.Image // Importa componente para exibir imagens
import androidx.compose.foundation.background // Importa componente para fundo de elementos
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment // Importa alinhamento de componentes
import androidx.compose.ui.Modifier // Importa modificador para alterar componentes
import androidx.compose.ui.graphics.Color // Importa classe para cores
import androidx.compose.ui.graphics.painter.Painter // Importa classe para pintar imagens
import androidx.compose.ui.res.colorResource // Importa função para obter cores de recursos
import androidx.compose.ui.res.painterResource // Importa função para obter imagens de recursos
import androidx.compose.ui.text.font.FontWeight // Importa peso da fonte
import androidx.compose.ui.unit.dp // Importa unidade de densidade (dp)
import androidx.compose.ui.unit.sp // Importa unidade de tamanho de fonte (sp)
import com.example.biblion.Domain.Endereco
import com.example.biblion.Helper.EnderecoClient
import com.example.biblion.R // Importa recursos do projeto
import kotlinx.coroutines.launch

@Composable
fun DeliveryInfoBox() {
    var cep by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var numeroErro by remember { mutableStateOf(false) }
    var cepErro by remember { mutableStateOf(false) }
    var endereco by remember { mutableStateOf(Endereco()) }
    val scope = rememberCoroutineScope()

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
                onValueChange = {
                    cep = it
                    cepErro = false
                },
                label = { Text("CEP") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    scope.launch {
                        try {
                            val result = EnderecoClient.enderecoAPI.getEnderecoByCEP(cep)
                            if (result.logradouro.isNullOrEmpty()) {
                                cepErro = true
                                endereco = Endereco() // reset
                            } else {
                                endereco = result
                                cepErro = false
                            }
                        } catch (e: Exception) {
                            cepErro = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.pink)
                )
            ) {
                Text(text = "Buscar")
            }
        }

        // Erro de CEP
        if (cepErro) {
            Text(
                text = "CEP inválido ou não encontrado.",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campos preenchidos pela API
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

        // Campo de número
        Text(text = "Número:")
        TextField(
            value = numero,
            onValueChange = {
                numero = it
                numeroErro = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Número") },
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

    // Botão "Fazer pedido"
    Button(
        onClick = {
            numeroErro = numero.isBlank()
            if (!numeroErro && !cepErro) {
                // Aqui você pode prosseguir com o pedido
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