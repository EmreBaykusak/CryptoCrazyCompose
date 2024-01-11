package com.example.cryptocrazycompose.repository

import com.example.cryptocrazycompose.model.CryptoIconList
import com.example.cryptocrazycompose.model.CryptoIconListItem
import com.example.cryptocrazycompose.model.CryptoList
import com.example.cryptocrazycompose.model.CryptoListItem
import com.example.cryptocrazycompose.service.CryptoAPI
import com.example.cryptocrazycompose.util.Constants.API_KEY
import com.example.cryptocrazycompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api : CryptoAPI
){
    suspend fun getCryptoList() : Resource<CryptoList>{
        val response = try {
            api.getCryptoList(API_KEY)
        } catch (e: Exception){
            return Resource.Error("Error.")
        }
        return Resource.Success(response)
    }

    suspend fun getCryptoIcon(assetId : String) : Resource<CryptoIconListItem>{
        val response = try {
            api.getIconList(32, API_KEY)
        } catch (e : Exception){
            return Resource.Error("${e.message}")
        }
        val iconItem = response.firstOrNull{it.asset_id == assetId}

        return if (iconItem != null){
            Resource.Success(iconItem)
        } else{
            Resource.Error("Crypto Icon List Item  with asset_id '$assetId' not found")
        }
    }
}