package com.example.biblion.Domain

data class CategoryModel( // Cria uma classe de dados chamada CategoryModel
    var CategoryId:Int=0, // Variável para o ID da categoria, inicia com valor 0
    var image:String="", // Variável para o caminho da imagem, inicia como uma string vazia
    var CategoryName:String="" // Variável para o nome da categoria, inicia como uma string vazia
)