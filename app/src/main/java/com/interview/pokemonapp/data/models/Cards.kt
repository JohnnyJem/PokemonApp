package com.interview.pokemonapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cards(val cards: List<Card>)



