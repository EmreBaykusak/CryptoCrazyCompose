package com.example.cryptocrazycompose.service

import com.example.cryptocrazycompose.model.CryptoIconList
import com.example.cryptocrazycompose.model.CryptoList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoAPI {
    // /v1/assets
    // https://rest.coinapi.io/v1/assets/{asset_id}
    // https://rest.coinapi.io/v1/assets/icons/{iconSize}

    @GET("assets")
    suspend fun getCryptoList(
        @Query("apikey") key : String
    ) : CryptoList

    @GET("assets/icons/{iconSize}")
    suspend fun getIconList(
        @Path("iconSize") iconSize : Int,
        @Query("apikey") key : String
    ) : CryptoIconList
}