package com.example.binlisttest

import com.example.binlisttest.models.BinInfo
import com.example.binlisttest.network.Networking

class Repository {

    suspend fun getBinInfo(number: String): BinInfo {
        return Networking.api.getInfoByBin(number)
    }
}