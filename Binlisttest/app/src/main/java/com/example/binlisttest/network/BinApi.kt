package com.example.binlisttest.network

import com.example.binlisttest.models.BinInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {

    @GET("/{number}")
    suspend fun getInfoByBin(
    @Path("number") number: String
    ): BinInfo
}