package com.example.biblion.Helper

fun interface ChangeNumberItemsListener { // Declara uma interface funcional que pode ser usada como lambda
    fun onChanged() // Método que será chamado quando ocorrer uma mudança, sem parâmetros
} // Fim da interface