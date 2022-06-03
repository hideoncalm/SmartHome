package com.quyen.smarthome.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.quyen.smarthome.R
import com.quyen.smarthome.utils.Constant.DATE_TIME_FORMAT
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

fun getTimeFormat(): String = Calendar.getInstance().time.toString(DATE_TIME_FORMAT)

fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)
