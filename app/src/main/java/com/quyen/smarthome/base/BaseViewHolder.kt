package com.quyen.smarthome.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(
    binding: ViewBinding,
    onItemClick: (T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    var item: T? = null

    init {
        binding.root.setOnClickListener {
            item?.let(onItemClick)
        }
    }

    open fun onBindData(item: T) {
        this.item = item
    }
}
