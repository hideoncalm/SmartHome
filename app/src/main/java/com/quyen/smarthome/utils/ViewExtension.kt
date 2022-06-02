package com.quyen.smarthome.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.quyen.smarthome.R
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImageFromUrl(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_living_room)
        .error(R.drawable.ic_living_room)
        .into(this)
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}