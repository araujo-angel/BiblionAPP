package com.example.biblion.Splash

import androidx.compose.foundation.border // Importa a função para criar bordas em componentes
import androidx.compose.foundation.layout.Row // Importa para organizar componentes em uma linha horizontal
import androidx.compose.foundation.layout.fillMaxWidth // Importa para fazer componentes preencherem toda a largura disponível
import androidx.compose.foundation.layout.height // Importa para definir a altura dos componentes
import androidx.compose.foundation.layout.padding // Importa para adicionar espaço interno (margem) aos componentes
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape // Importa para criar cantos arredondados
import androidx.compose.material.Text // Importa para exibir textos na tela
import androidx.compose.material3.Button // Importa o componente de botão do Material Design 3
import androidx.compose.material3.ButtonDefaults // Importa configurações padrão para botões
import androidx.compose.runtime.Composable // Importa para marcar funções como componentes reutilizáveis do Compose
import androidx.compose.ui.Modifier // Importa para modificar atributos de componentes
import androidx.compose.ui.graphics.Color // Importa para usar cores
import androidx.compose.ui.res.colorResource // Importa para usar cores definidas em recursos
import androidx.compose.ui.tooling.preview.Preview // Importa para permitir visualização do componente no preview do IDE
import androidx.compose.ui.unit.dp // Importa para definir tamanhos em density-independent pixels
import androidx.compose.ui.unit.sp // Importa para definir tamanhos de fonte em scale-independent pixels
import com.example.biblion.R // Importa o arquivo de recursos onde estão definidas as cores e outros recursos

@Composable
@Preview
fun GetStartedButton(onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.pink)
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Iniciar",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}
