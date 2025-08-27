package com.example.biblion.Domain

import java.io.Serializable // Importa a interface Serializable para permitir que objetos da classe sejam convertidos em um formato que pode ser salvo ou enviado

// Define uma classe de dados chamada FoodModel que representa um livro
data class BookModel(
    var Bestseller: Boolean = false, // Indica se o livro é destaque, padrão false
    var CategoryId: String = "", // ID da categoria do livro, padrão vazio
    var Description: String = "", // Descrição do livro, padrão vazio
    var Id: Int = 0, // ID único do livro, padrão 0
    var ImagePath: String = "", // Caminho da imagem do livro, padrão vazio
    var EditoraId: Int = 0, // ID da localização, padrão 0
    var Price: Double = 0.0, // Preço do livro, padrão 0.0
    var PriceId: Int = 0, // ID do preço, padrão 0
    var TempoLeitura: Double = 0.0,
    var TimeId: Int = 0, // ID relacionado ao tempo, padrão 0
    var Paginas: Int = 0, // Valor de tempo, padrão 0
    var Title: String = "", // Título ou nome do livro, padrão vazio
    var Ano: Int = 0, // Calorias do livro, padrão 0
    var numberInCart: Int = 0 // Quantidade no carrinho, padrão 0
) : Serializable // A classe implementa Serializable para permitir salvar ou transmitir objetos dessa classe