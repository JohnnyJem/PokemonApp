package com.interview.pokemonapp.presentation

import android.util.Log
import com.interview.pokemonapp.data.PokemonApi
import com.interview.pokemonapp.data.models.Card
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.*


class ListPokemonPresenter {
    private val BASE_URL = "https://api.pokemontcg.io"
    private val CLASS_TAG = "ListPokemonPresenter"

    private lateinit var retrofit: Retrofit
    private lateinit var pokemonApiRetrofitClient: PokemonApi

    private var view: ListPokemonView? = null
    private var cardsList: List<Card> = listOf()
    private var lastSearchedForCard: String? = null
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var isLoading: Boolean = false

    fun onCreated(view: ListPokemonView) {
        this.view = view
        isLoading = false

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        pokemonApiRetrofitClient = retrofit.create(PokemonApi::class.java)
    }

    fun onResumed() {
        if (!isLoading) {
            if (cardsList.isNotEmpty()) {
                view?.setListOfCards(cardsList)
            } else {
                requestCards(lastSearchedForCard)
            }
        } else {
            view?.showLoading()
        }
    }

    fun onCleared() {
        view = null
        isLoading = false
        compositeDisposable.dispose()
    }

    fun requestCards(name: String?) {
        isLoading = true // TODO: fix potential race condition(not seen yet)
        lastSearchedForCard = name
        val disposable = hasInternetConnection()
            .filter { isOnline ->
                Log.d("Presenter", "Filter : $isOnline")
                isOnline
            }
            .singleOrError()
            .toObservable()
            .flatMap {
                pokemonApiRetrofitClient.getListOfCards(name)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view?.showLoading()
            }
            .doFinally() {
                isLoading = false
                view?.hideLoading()
            }
            .subscribe(
                { response ->
                    cardsList = response.cards
                    view?.setListOfCards(cardsList)
                },
                { error ->
                    // USED SEALED CLASSES HERE FOR ERRORS.
                    // here we handle errors including: non-2XX responses, HttpException, IOException
                    // no element exception = no network
                    when (error) {
                        is NoSuchElementException -> view?.showNetworkUnavailableError()
                        is HttpException -> view?.showLoadingError()
                        is IOException -> view?.showLoadingError()
                        else -> view?.showLoadingError()
                    }
                    Log.e(
                        CLASS_TAG,
                        "onError: $error.message"
                    )
                }
            )

        compositeDisposable.add(disposable)
    }

    private fun hasInternetConnection(): Observable<Boolean?> {
        return Observable.fromCallable { view?.isInternetAvailable() }
    }

}