package com.interview.pokemonapp.presentation

import com.interview.pokemonapp.data.models.Card

interface ListPokemonView {
    fun setListOfCards(cards: List<Card>)
    fun showLoading()
    fun hideLoading()
    fun showLoadingError()
    fun showNetworkUnavailableError()
    fun hideKeyboard()
    fun isInternetAvailable(): Boolean

}