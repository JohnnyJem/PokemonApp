package com.interview.pokemonapp.presentation


import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat.getActionView
import androidx.recyclerview.widget.GridLayoutManager
import com.interview.pokemonapp.R
import com.interview.pokemonapp.data.models.Card
import kotlinx.android.synthetic.main.activity_main.*


class ListPokemonActivity : AppCompatActivity(), ListPokemonHeadlessFragment.ListFragmentCallback {
    private val SPAN_COUNT = 4;
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: RecyclerViewAdapter
    private var headlessFragment: ListPokemonHeadlessFragment? = null
    private var recyclerViewDataList: ArrayList<Card> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        val manager = supportFragmentManager
        headlessFragment =
            manager.findFragmentByTag(ListPokemonHeadlessFragment.TAG) as? ListPokemonHeadlessFragment

        if (headlessFragment == null) {
            headlessFragment = ListPokemonHeadlessFragment.newInstance()
            val transaction = manager.beginTransaction()
            transaction.add(headlessFragment!!, ListPokemonHeadlessFragment.TAG)
            transaction.commit()
        }

        gridLayoutManager = GridLayoutManager(this, SPAN_COUNT)
        recyclerView.layoutManager = gridLayoutManager

        adapter = RecyclerViewAdapter(recyclerViewDataList)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun setListOfCards(cards: List<Card>) {
        recyclerViewDataList.clear()
        recyclerViewDataList.addAll(cards)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun showLoadingError() {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
    }

    override fun showNetworkUnavailableError() {
        Toast.makeText(this, getString(R.string.please_connect_to_the_internet), Toast.LENGTH_LONG).show()
    }

    override fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Suppress("DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchItem.expandActionView()
        val searchView = getActionView(searchItem) as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (headlessFragment as ListPokemonHeadlessFragment).getPresenter().requestCards(query)
                searchView.clearFocus() // needed here because search button on the keypad of the emulator generates two outputs
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

}
