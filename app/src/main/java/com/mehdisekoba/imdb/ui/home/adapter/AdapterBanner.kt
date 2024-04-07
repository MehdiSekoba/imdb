@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.mehdisekoba.imdb.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.animation.AnimationUtils.lerp
import com.mehdisekoba.imdb.data.model.home.ResponsePopular.Result
import com.mehdisekoba.imdb.databinding.ItemBannerBinding
import com.mehdisekoba.imdb.utils.BANNER_COUNT
import com.mehdisekoba.imdb.utils.TMDB_IMAGE_BASE_URL_W780
import com.mehdisekoba.imdb.utils.base.BaseDiffUtils
import com.mehdisekoba.imdb.utils.extensions.loadImage
import javax.inject.Inject

class AdapterBanner
    @Inject
    constructor() : RecyclerView.Adapter<AdapterBanner.ViewHolder>() {
        private lateinit var binding: ItemBannerBinding
        private var items = emptyList<Result>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): ViewHolder {
            binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder()
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int,
        ) = holder.bind(items[position])

        override fun getItemCount() = if (items.size > BANNER_COUNT) BANNER_COUNT else items.size

        override fun getItemViewType(position: Int) = position

        override fun getItemId(position: Int) = position.toLong()

        inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("RestrictedApi")
            fun bind(item: Result) {
                binding.apply {
                    // text
                    txtTitle.text = item.title
                    carouselItemContainer.setOnMaskChangedListener { maskRect ->
                        txtTitle.translationX = maskRect.left
                        txtTitle.setAlpha(lerp(1F, 0F, 0F, 80F, maskRect.left))
                    }
                    val moviePosterURL = TMDB_IMAGE_BASE_URL_W780 + item.backdropPath
                    carouselImageView.loadImage(moviePosterURL)
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
