package com.example.cryptocrazycompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptocrazycompose.model.CryptoIconListItem
import com.example.cryptocrazycompose.repository.CryptoRepository
import com.example.cryptocrazycompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository : CryptoRepository
) : ViewModel() {
    suspend fun getCryptoIcon(id : String): Resource<CryptoIconListItem> {
        return repository.getCryptoIcon(id)
    }
}