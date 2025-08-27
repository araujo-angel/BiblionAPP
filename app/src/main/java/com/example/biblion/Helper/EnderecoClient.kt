package com.example.biblion.Helper

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EnderecoClient {
    companion object{
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val enderecoAPI = retrofit.create(EnderecoAPI::class.java)
    }
}