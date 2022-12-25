package com.example.binlisttest.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BinInfo(
    val number: Bin?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country?,
    val bank: Bank?
)
