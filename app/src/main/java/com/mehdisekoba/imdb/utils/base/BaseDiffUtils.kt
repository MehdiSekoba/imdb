package com.mehdisekoba.imdb.utils.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtils<T>(private val oldItem: List<T>, private val newItems: List<T>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItem.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean {
        return oldItem[oldItemPosition] === newItems[newItemPosition]
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean {
        return oldItem[oldItemPosition] === newItems[newItemPosition]
    }
}
