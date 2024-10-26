package com.example.backpackgps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.backpackgps.models.Dispositivo

class DispositivoViewModel : ViewModel() {
    private val _dispositivos = MutableLiveData<MutableList<Dispositivo>>(mutableListOf())
    val dispositivos: LiveData<MutableList<Dispositivo>> get() = _dispositivos

    fun addDispositivo(dispositivo: Dispositivo) {
        _dispositivos.value?.add(dispositivo)
        _dispositivos.value = _dispositivos.value // Notifica cambios
    }

    fun removeDispositivo(dispositivo: Dispositivo) {
        _dispositivos.value?.remove(dispositivo)
        _dispositivos.value = _dispositivos.value // Notifica cambios
    }
}

