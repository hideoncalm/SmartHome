package com.quyen.smarthome.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
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

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.apply {
        setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        setGravity(Gravity.BOTTOM)
    }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getTimeFormat(): String = Calendar.getInstance().time.toString(DATE_TIME_FORMAT)

fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)
