package com.interview.pokemonapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Resistance(
    val type: String,
    val value: String
)