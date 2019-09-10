package com.interview.pokemonapp.presentation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.interview.pokemonapp.R
import com.interview.pokemonapp.data.models.Card
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerViewAdapter(private val cards: ArrayList<Card>) :
    RecyclerView.Adapter<RecyclerViewAdapter.CardHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CardHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return CardHolder(inflatedView)
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val card = cards[position]
        holder.bindCard(card)
    }

    class CardHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var card: Card? = null

        init {
            v.setOnClickListener(this)
        }

        fun bindCard(card: Card) {
            this.card = card
            Glide.with(view.context).load(card.imageUrl).into(view.itemImage)
        }

        override fun onClick(v: View) {
            val context = itemView.context
            val showCardIntent = Intent(context, TransparentCardViewActivity::class.java)
            card?.imageUrlHiRes?.let {
                showCardIntent.putExtra(CARD_KEY, card?.imageUrlHiRes)
                context.startActivity(showCardIntent)
            }
        }

        companion object {
            private val CARD_KEY = "CARD"
        }
    }

}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
