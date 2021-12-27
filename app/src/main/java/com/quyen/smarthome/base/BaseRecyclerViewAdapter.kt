package com.quyen.smarthome.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<T, VH : BaseViewHolder<T>>(
    private val diffUtil: DiffUtil.ItemCallback<T>,
    private val onItemClick: (T) -> Unit
) : ListAdapter<T, VH>(diffUtil) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBindData(getItem(position))
    }

    fun updateData(newData: MutableList<T>) {
        submitList(newData)
    }
}
