package com.interview.pokemonapp.data

import com.interview.pokemonapp.data.models.Cards
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("v1/cards")
    fun getListOfCards(@Query("name") name: String?): Observable<Cards>
}