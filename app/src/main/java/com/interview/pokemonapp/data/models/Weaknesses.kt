package com.interview.pokemonapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weaknesses(
    val type: String,
    val value: String
)