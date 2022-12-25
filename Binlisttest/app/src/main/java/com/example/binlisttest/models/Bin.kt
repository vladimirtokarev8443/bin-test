package com.example.binlisttest.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bin(
    val length: Int?,
    val luhn: Boolean?
)
