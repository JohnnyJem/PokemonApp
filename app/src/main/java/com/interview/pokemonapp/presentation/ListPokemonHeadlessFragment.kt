package com.interview.pokemonapp.presentation

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.interview.pokemonapp.R
import com.interview.pokemonapp.data.models.Card
import java.net.InetAddress


class ListPokemonHeadlessFragment : Fragment(), ListPokemonView {
    private lateinit var listPokemonPresenter: ListPokemonPresenter
    private var mActivity: Activity? = null
    private var mCallback: ListFragmentCallback? = null

    interface ListFragmentCallback {
        fun setListOfCards(cards: List<Card>)
        fun showLoading()
        fun hideLoading()
        fun showLoadingError()
        fun showNetworkUnavailableError()
        fun hideKeyboard()
    }

    override fun setListOfCards(cards: List<Card>) {
        mCallback?.setListOfCards(cards)
    }

    override fun showLoading() {
        mCallback?.showLoading()
    }

    override fun hideLoading() {
        mCallback?.hideLoading()
    }

    override fun showLoadingError() {
        mCallback?.showLoadingError()
    }

    override fun showNetworkUnavailableError() {
        mCallback?.showNetworkUnavailableError()
    }

    override fun hideKeyboard() {
        mCallback?.hideKeyboard()
    }

    override fun isInternetAvailable(): Boolean {
        return try {
            // checks for actual internet connectivity, not just network presence.
            val ipAddr = InetAddress.getByName("google.com")
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }

    fun getPresenter(): ListPokemonPresenter {
        return listPokemonPresenter
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mActivity = activity
        if (activity is ListFragmentCallback) {
            mCallback = activity
        } else {
            throw IllegalArgumentException("activity must implement implement callback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
        mCallback = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        listPokemonPresenter = ListPokemonPresenter()
        listPokemonPresenter.onCreated(this)

        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (isInternetAvailable()) {
                    listPokemonPresenter.onResumed()
                }
            }

            override fun onLost(network: Network?) {
                Toast.makeText(
                    context,
                    getString(R.string.please_connect_to_the_internet),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        listPokemonPresenter.onCleared()
    }

    override fun onResume() {
        super.onResume()
        listPokemonPresenter.onResumed()
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {
        const val TAG = "ListPokemonHeadlessFragment"
        fun newInstance(): ListPokemonHeadlessFragment {
            return ListPokemonHeadlessFragment()
        }
    }

}