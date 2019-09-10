package com.interview.pokemonapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Card(
    val artist: String = "",
    val attacks: List<Attack> = listOf(),
    val convertedRetreatCost: Int = 0,
    val hp: String = "None",
    val id: String = "",
    val imageUrl: String = "",
    val imageUrlHiRes: String = "",
    val name: String = "",
    val nationalPokedexNumber: Int = 0,
    val number: String = "",
    val rarity: String ="",
    val resistances: List<Resistance> = listOf(),
    val retreatCost: List<String> = listOf(),
    val series: String,
    val `set`: String,
    val setCode: String,
    val subtype: String = "",
    val supertype: String,
    val text: List<String> = listOf(),
    val types: List<String> = listOf(),
    val weaknesses: List<Weaknesses> = listOf()
)