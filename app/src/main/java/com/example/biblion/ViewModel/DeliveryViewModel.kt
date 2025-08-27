package com.example.biblion.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biblion.Domain.Endereco
import com.example.biblion.Helper.EnderecoClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeliveryViewModel : ViewModel() {

    private val _cep = MutableStateFlow("")
    val cep: StateFlow<String> = _cep

    private val _numero = MutableStateFlow("")
    val numero: StateFlow<String> = _numero

    private val _cepErro = MutableStateFlow(false)
    val cepErro: StateFlow<Boolean> = _cepErro

    private val _numeroErro = MutableStateFlow(false)
    val numeroErro: StateFlow<Boolean> = _numeroErro

    private val _endereco = MutableStateFlow(Endereco())
    val endereco: StateFlow<Endereco> = _endereco

    fun onCepChange(newCep: String) {
        _cep.value = newCep
        _cepErro.value = false
    }

    fun onNumeroChange(newNumero: String) {
        _numero.value = newNumero
        _numeroErro.value = false
    }

    fun buscarCep() {
        viewModelScope.launch {
            try {
                val result = EnderecoClient.enderecoAPI.getEnderecoByCEP(_cep.value)
                if (result.logradouro.isNullOrEmpty()) {
                    _cepErro.value = true
                    _endereco.value = Endereco()
                } else {
                    _endereco.value = result
                    _cepErro.value = false
                }
            } catch (e: Exception) {
                _cepErro.value = true
            }
        }
    }

    fun validarCampos(): Boolean {
        val numeroValido = _numero.value.isNotBlank()
        _numeroErro.value = !numeroValido
        return numeroValido && !_cepErro.value
    }
}
