package com.mehdisekoba.imdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehdisekoba.imdb.data.model.home.ResponseMovies.Result
import com.mehdisekoba.imdb.databinding.ItemPopularBinding
import com.mehdisekoba.imdb.utils.ITEM_COUNT
import com.mehdisekoba.imdb.utils.TMDB_IMAGE_BASE_URL_W780
import com.mehdisekoba.imdb.utils.base.BaseDiffUtils
import com.mehdisekoba.imdb.utils.extensions.loadImage
import com.mehdisekoba.imdb.utils.extensions.toTwoDecimals
import javax.inject.Inject

class AdapterTvOnTheAir
    @Inject
    constructor() : RecyclerView.Adapter<AdapterTvOnTheAir.ViewHolder>() {
        private lateinit var binding: ItemPopularBinding
        private var items = emptyList<Result>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): ViewHolder {
            binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder()
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int,
        ) = holder.bind(items[position])

        override fun getItemCount() = ITEM_COUNT

        override fun getItemViewType(position: Int) = position

        override fun getItemId(position: Int) = position.toLong()

        inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Result) {
                binding.apply {
                    // text
                    txtTitle.text = item.name
                    val vote = item.voteAverage!!.toTwoDecimals()
                    txtRating.text = vote
                    val moviePosterURL = TMDB_IMAGE_BASE_URL_W780 + item.posterPath
                    itemImg.loadImage(moviePosterURL)
                    // click
                    root.setOnClickListener {
                        // Click
                        onItemClickListener?.let { it(item) }
                    }
                }
            }
        }

        private var onItemClickListener: ((Result) -> Unit)? = null

        fun setOnItemClickListener(listener: (Result) -> Unit) {
            onItemClickListener = listener
        }

        fun setData(data: List<Result>) {
            val adapterDiffUtils = BaseDiffUtils(items, data)
            val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
            items = data
            diffUtils.dispatchUpdatesTo(this)
        }
    }
