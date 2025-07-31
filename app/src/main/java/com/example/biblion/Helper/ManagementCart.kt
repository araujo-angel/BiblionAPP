package com.example.biblion.Helper

import android.content.Context // importa a classe Context do Android
import android.widget.Toast // importa para mostrar mensagens na tela
import com.example.biblion.Domain.BookModel // importa o modelo de alimento
import com.example.biblion.Helper.ChangeNumberItemsListener // importa a interface para mudanças no número de itens
import kotlin.collections.indexOfFirst // importa a função para encontrar o índice de um item na lista

class ManagementCart(val context: Context) { // classe que gerencia o carrinho de compras

    private val tinyDB = TinyDB(context) // instancia do TinyDB para armazenamento local

    fun insertItem(item: BookModel) { // método para inserir ou atualizar um item no carrinho
        var listFood = getListCart() // obtém a lista atual do carrinho
        val existAlready = listFood.any { it.Title == item.Title } // verifica se o item já existe na lista pelo título
        val index = listFood.indexOfFirst { it.Title == item.Title } // encontra o índice do item na lista

        if (existAlready) { // se o item já existe
            listFood[index].numberInCart = item.numberInCart // atualiza a quantidade
        } else { // se não existe
            listFood.add(item) // adiciona o novo item
        }
        tinyDB.putListObject("CartList", listFood) // salva a lista atualizada no armazenamento local
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show() // mostra mensagem de confirmação
    }

    fun getListCart(): ArrayList<BookModel> { // método para obter a lista do carrinho
        return tinyDB.getListObject("CartList") ?: arrayListOf() // retorna a lista ou uma vazia se não existir
    }

    fun minusItem(listFood: ArrayList<BookModel>, position: Int, listener: ChangeNumberItemsListener) { // método para diminuir a quantidade de um item
        if (position < 0 || position >= listFood.size) return // se a posição for inválida, sai do método
        val currentCount = listFood[position].numberInCart // pega a quantidade atual
        if (currentCount <= 1) { // se a quantidade for 1 ou menos
            listFood.removeAt(position) // remove o item da lista
        } else { // se houver mais de 1
            listFood[position].numberInCart = currentCount - 1 // diminui a quantidade
        }
        tinyDB.putListObject("CartList", listFood) // atualiza o armazenamento local
        listener.onChanged() // informa que a quantidade mudou
    }

    fun plusItem(listFood: ArrayList<BookModel>, position: Int, listener: ChangeNumberItemsListener) { // método para aumentar a quantidade
        listFood[position].numberInCart++ // aumenta a quantidade do item
        tinyDB.putListObject("CartList", listFood) // salva a lista atualizada
        listener.onChanged() // informa que a quantidade mudou
    }

    fun getTotalFee(): Double { // método para calcular o valor total do carrinho
        val listFood = getListCart() // obtém a lista do carrinho
        var fee = 0.0 // inicializa o valor total
        for (item in listFood) { // para cada item na lista
            fee += item.Price * item.numberInCart // soma o preço multiplicado pela quantidade
        }
        return fee // retorna o valor total
    }
}