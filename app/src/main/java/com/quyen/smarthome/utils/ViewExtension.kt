package com.quyen.smarthome.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.quyen.smarthome.R

fun ImageView.loadImageFromUrl(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_living_room)
        .error(R.drawable.ic_living_room)
        .into(this)
}
