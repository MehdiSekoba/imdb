package com.mehdisekoba.imdb.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehdisekoba.imdb.data.model.detail.ResponseDetail.Genre
import com.mehdisekoba.imdb.databinding.ItemGenreBinding
import com.mehdisekoba.imdb.utils.base.BaseDiffUtils
import javax.inject.Inject

class AdapterGenre
    @Inject
    constructor() : RecyclerView.Adapter<AdapterGenre.ViewHolder>() {
        // Binding
        private lateinit var binding: ItemGenreBinding
        private var items = emptyList<Genre>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): ViewHolder {
            binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder()
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int,
        ) = holder.bind(items[position])

        override fun getItemCount() = items.size

        override fun getItemViewType(position: Int) = position

        override fun getItemId(position: Int) = position.toLong()

        inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Genre) {
                binding.apply {
                    item.let {
                        txtGenres.text = it.name
                    }
                }
            }
        }

        fun setData(data: List<Genre>) {
            val adapterDiffUtils = BaseDiffUtils(items, data)
            val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
            items = data
            diffUtils.dispatchUpdatesTo(this)
        }
    }
