package com.quyen.smarthome.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.quyen.smarthome.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CustomProgressBar @Inject constructor() {

    @Inject
    lateinit var dialog: CustomDialog

    @SuppressLint("InflateParams")
    fun showProgressBar(activity: Activity) {
        val inflater = activity.layoutInflater
        dialog = CustomDialog(activity)
        dialog.apply {
            setContentView(inflater.inflate(R.layout.custom_progress_bar, null))
            show()
        }
    }

    fun dismissProgressBar() {
        dialog.dismiss()
    }

    class CustomDialog @Inject constructor(
        @ApplicationContext context: Context
    ) :
        Dialog(context, R.style.CustomDialogTheme) {
        init {
            window?.let { it.decorView.rootView?.setBackgroundResource(R.color.color_semi_transparent) }
            window?.let {
                it.decorView.setOnApplyWindowInsetsListener { _, insets ->
                    insets.consumeSystemWindowInsets()
                }
            }
        }
    }
}