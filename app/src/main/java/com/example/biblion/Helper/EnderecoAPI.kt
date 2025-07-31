package com.example.biblion.Helper

import com.example.biblion.Domain.Endereco
import retrofit2.http.GET
import retrofit2.http.Path

interface EnderecoAPI {
    @GET("/ws/{cep}/json/")
    suspend fun getEnderecoByCEP(@Path("cep") cep: String): Endereco
}