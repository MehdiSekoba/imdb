package com.mehdisekoba.imdb.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehdisekoba.imdb.data.model.onboarding.IntroSlide
import com.mehdisekoba.imdb.databinding.ItemOnboardingBinding
import com.mehdisekoba.imdb.utils.base.BaseDiffUtils
import javax.inject.Inject

class AdapterOnBoarding
    @Inject
    constructor() : RecyclerView.Adapter<AdapterOnBoarding.ViewHolder>() {
        // Binding
        private lateinit var binding: ItemOnboardingBinding
        private var items = emptyList<IntroSlide>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): ViewHolder {
            binding = ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder()
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int,
        ) = holder.bind(items[position])

        override fun getItemCount() = items.size

        inner class ViewHolder :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: IntroSlide) {
                binding.apply {
                    // lottie
                    animation.setAnimation(item.iconResourceId)
                    // title
                    txtTitle.text = item.title
                    txtDesc.text = item.description
                }
            }
        }

        fun setData(data: List<IntroSlide>) {
            val adapterDiffUtils = BaseDiffUtils(items, data)
            val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
            items = data
            diffUtils.dispatchUpdatesTo(this)
        }
    }
