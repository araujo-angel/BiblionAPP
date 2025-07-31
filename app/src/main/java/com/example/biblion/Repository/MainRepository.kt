package com.example.biblion.Repository

import androidx.lifecycle.LiveData // importa a classe LiveData para observar dados
import androidx.lifecycle.MutableLiveData // importa MutableLiveData para modificar os dados observáveis
import com.google.firebase.database.DataSnapshot // importa DataSnapshot do Firebase para manipular os dados recebidos
import com.google.firebase.database.DatabaseError // importa DatabaseError para tratar erros do Firebase
import com.google.firebase.database.FirebaseDatabase // importa FirebaseDatabase para acessar o banco de dados Firebase
import com.google.firebase.database.Query // importa Query para fazer consultas específicas no Firebase
import com.google.firebase.database.ValueEventListener // importa ValueEventListener para ouvir alterações no banco de dados
import com.example.biblion.Domain.BannerModel // importa o modelo Banner
import com.example.biblion.Domain.CategoryModel // importa o modelo Category
import com.example.biblion.Domain.BookModel // importa o modelo Food
import kotlin.jvm.java // (não utilizado neste código, poderia ser removido)
import kotlin.let // importa a função let do Kotlin para executar blocos de código seguros

class MainRepository { // define a classe repositório que gerencia os dados

    private val firebaseDatabase = FirebaseDatabase.getInstance() // instancia o FirebaseDatabase para acessar o banco

    fun loadBanner(): LiveData<MutableList<BannerModel>> { // função para carregar banners do banco
        val listData = MutableLiveData<MutableList<BannerModel>>() // cria um LiveData mutável para a lista de banners
        val ref = firebaseDatabase.getReference("Banners") // referência ao nó "Banners" no Firebase
        ref.addValueEventListener(object : ValueEventListener { // adiciona ouve as mudanças nos dados de banners
            override fun onDataChange(snapshot: DataSnapshot) { // quando os dados mudarem
                val list = mutableListOf<BannerModel>() // cria uma lista mutável de banners
                for (childSnapshot in snapshot.children) { // percorre cada filho na resposta
                    val item = childSnapshot.getValue(BannerModel::class.java) // converte o dado para BannerModel
                    item?.let { list.add(it) } // se não for nulo, adiciona na lista
                }
                listData.value = list // atualiza o LiveData com a nova lista
            }

            override fun onCancelled(error: DatabaseError) { // se houver erro na leitura
                TODO("Not yet implemented") // placeholder para tratamento de erro (ainda não implementado)
            }

        })
        return listData // retorna o LiveData com os banners
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> { // função para carregar categorias
        val listData = MutableLiveData<MutableList<CategoryModel>>() // cria LiveData para categorias
        val ref = firebaseDatabase.getReference("Category") // referência ao nó "Category"
        ref.addValueEventListener(object : ValueEventListener { // ouve mudanças nas categorias
            override fun onDataChange(snapshot: DataSnapshot) { // quando os dados mudarem
                val list = mutableListOf<CategoryModel>() // cria lista de categorias
                for (childSnapshot in snapshot.children) { // percorre cada categoria
                    val item = childSnapshot.getValue(CategoryModel::class.java) // converte para CategoryModel
                    item?.let { list.add(it) } // adiciona na lista se não for nulo
                }
                listData.value = list // atualiza o LiveData
            }

            override fun onCancelled(error: DatabaseError) { // se ocorrer erro
                TODO("Not yet implemented") // placeholder para tratamento
            }

        })
        return listData // retorna o LiveData com categorias
    }

    fun loadFiltered(id: String): LiveData<MutableList<BookModel>> { // função para carregar alimentos filtrados por categoria
        val listData = MutableLiveData<MutableList<BookModel>>() // cria LiveData para alimentos
        val ref = firebaseDatabase.getReference("Books") // referência ao nó "Foods"
        val query: Query = ref.orderByChild("CategoryId").equalTo(id) // faz consulta onde CategoryId é igual ao id recebido
        query.addListenerForSingleValueEvent(object : ValueEventListener { // ouve uma única leitura dos dados filtrados
            override fun onDataChange(snapshot: DataSnapshot) { // quando os dados forem recebidos
                val lists = mutableListOf<BookModel>() // cria lista de alimentos
                for (childSnapshot in snapshot.children) { // percorre cada alimento filtrado
                    val list = childSnapshot.getValue(BookModel::class.java) // converte para BookModel
                    if (list != null) { // se a conversão for bem-sucedida
                        lists.add(list) // adiciona na lista
                    }
                }
                listData.value = lists // atualiza o LiveData
            }

            override fun onCancelled(error: DatabaseError) { // se ocorrer erro na consulta
                TODO("Not yet implemented") // placeholder para tratamento de erro
            }
        })
        return listData // retorna o LiveData com alimentos filtrados
    }

    fun loadBooks(): LiveData<MutableList<BookModel>> {
        val listData = MutableLiveData<MutableList<BookModel>>()
        val ref = firebaseDatabase.getReference("Books")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<BookModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(BookModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                // Trate o erro aqui se necessário
            }
        })

        return listData
    }
}