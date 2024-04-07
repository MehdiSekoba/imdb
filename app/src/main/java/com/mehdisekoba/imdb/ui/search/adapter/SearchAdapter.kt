@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.mehdisekoba.imdb.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar.Result
import com.mehdisekoba.imdb.databinding.ItemSearchBinding
import com.mehdisekoba.imdb.utils.TMDB_IMAGE_BASE_URL_W780
import com.mehdisekoba.imdb.utils.base.BaseDiffUtils
import com.mehdisekoba.imdb.utils.extensions.loadImageCircularProgress
import com.mehdisekoba.imdb.utils.extensions.toTwoDecimals
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchAdapter
    @Inject
    constructor(
        @ApplicationContext private var context: Context,
    ) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
        private lateinit var binding: ItemSearchBinding
        private var items = emptyList<Result>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): ViewHolder {
            binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            context = parent.context
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
            fun bind(item: Result) {
                binding.apply {
                    item.let {
                        val moviePosterURL = TMDB_IMAGE_BASE_URL_W780 + it.posterPath
                        imgPoster.loadImageCircularProgress(moviePosterURL)
                        txtTitle.text = it.title
                        val vote = item.voteAverage!!.toTwoDecimals()
                        rateTxt.text = vote
                        appRating.rating = it.voteAverage!!.toFloat() / 2
                        // click
                        root.setOnClickListener {
                            // Click
                            onItemClickListener?.let { it(item) }
                        }
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
