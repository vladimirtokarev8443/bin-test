package com.example.binlisttest.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    val numeric: String?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Int?,
    val longitude: Int?
)
