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

    private val _numeroErro = MutableStateFlow(false)
    val numeroErro: StateFlow<Boolean> = _numeroErro
    private val _uiState = MutableStateFlow<DeliveryUIState>(DeliveryUIState.Idle)
    val uiState: StateFlow<DeliveryUIState> = _uiState

    fun onCepChange(newCep: String) {
        _cep.value = newCep
        if (_uiState.value is DeliveryUIState.Error) {
            _uiState.value = DeliveryUIState.Idle
        }
    }

    fun onNumeroChange(newNumero: String) {
        _numero.value = newNumero
        _numeroErro.value = false
    }

    fun buscarCep() {
        viewModelScope.launch {
            _uiState.value = DeliveryUIState.Loading
            try {
                val result = EnderecoClient.enderecoAPI.getEnderecoByCEP(_cep.value)
                if (result.logradouro.isNullOrEmpty()) {
                    _uiState.value = DeliveryUIState.Error("CEP inválido ou não encontrado.")
                } else {
                    _uiState.value = DeliveryUIState.Success(result)
                }
            } catch (e: Exception) {
                _uiState.value = DeliveryUIState.Error(e.message ?: "Erro ao buscar CEP")
            }
        }
    }

    fun validarCampos(): Boolean {
        val numeroValido = _numero.value.isNotBlank()
        _numeroErro.value = !numeroValido

        return numeroValido && _uiState.value is DeliveryUIState.Success
    }
}
sealed class DeliveryUIState {
    object Idle : DeliveryUIState()
    data class Success(val endereco: Endereco) : DeliveryUIState()
    data class Error(val message: String? = null) : DeliveryUIState()
    object Loading : DeliveryUIState()
}

