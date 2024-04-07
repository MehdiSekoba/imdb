package com.mehdisekoba.imdb.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehdisekoba.imdb.data.model.detail.ResponseActor.Cast
import com.mehdisekoba.imdb.databinding.ItemActorBinding
import com.mehdisekoba.imdb.utils.TMDB_IMAGE_BASE_URL_W500
import com.mehdisekoba.imdb.utils.base.BaseDiffUtils
import com.mehdisekoba.imdb.utils.extensions.loadImage
import javax.inject.Inject

class AdapterActor
    @Inject
    constructor() : RecyclerView.Adapter<AdapterActor.ViewHolder>() {
        private lateinit var binding: ItemActorBinding
        private var items = emptyList<Cast>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): ViewHolder {
            binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            fun bind(item: Cast) {
                binding.apply {
                    item.let {
                        // text
                        txtTitle.text = it.character
                        val moviePosterURL = TMDB_IMAGE_BASE_URL_W500 + it.backdropPath
                        itemImg.loadImage(moviePosterURL)
                    }
                }
            }
        }

        fun setData(data: List<Cast>) {
            val adapterDiffUtils = BaseDiffUtils(items, data)
            val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
            items = data
            diffUtils.dispatchUpdatesTo(this)
        }
    }
