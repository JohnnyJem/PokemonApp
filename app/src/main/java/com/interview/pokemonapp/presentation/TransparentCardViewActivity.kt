package com.interview.pokemonapp.presentation

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.interview.pokemonapp.R
import kotlinx.android.synthetic.main.transparent_card_view_activity_main.*

class TransparentCardViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transparent_card_view_activity_main)
    }

    override fun onResume() {
        super.onResume()
        val ss: String? = intent.getStringExtra("CARD")

        ss.let {
            Glide.with(this)
                .load(ss)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progress_bar.visibility = View.GONE
                        return false
                    }
                })
                .into(imageView_card)
        }

    }
}