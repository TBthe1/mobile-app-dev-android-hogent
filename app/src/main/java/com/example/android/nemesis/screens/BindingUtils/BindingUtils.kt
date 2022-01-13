package com.example.android.nemesis.screens.BindingUtils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.nemesis.R
import com.example.android.nemesis.screens.gameOverviewFromAPI.GameApiStatus
import timber.log.Timber

// The adapter will adapt the game to get the data we need

// Adapter for imageURI
@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(this)
    }
}

// Adapt status to an image visibility
@BindingAdapter("gameApiStatus")
fun ImageView.bindStatus(status: GameApiStatus?) {
    when (status) {
        GameApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        GameApiStatus.ERROR -> {
            Timber.i("GameApiStatus.ERROR")
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }
        GameApiStatus.DONE -> {
            visibility = View.GONE
        }
    }
}