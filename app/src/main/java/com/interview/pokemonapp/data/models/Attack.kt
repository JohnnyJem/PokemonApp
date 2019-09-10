package com.interview.pokemonapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attack(
    val convertedEnergyCost: Int = 0,
    val cost: List<String> = listOf(),
    val damage: String = "",
    val name: String = "",
    val text: String = ""
)