package com.mehdisekoba.imdb.ui.allmovies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar.Result
import com.mehdisekoba.imdb.databinding.ItemAllMoviesBinding
import com.mehdisekoba.imdb.utils.TMDB_IMAGE_BASE_URL_W780
import com.mehdisekoba.imdb.utils.base.BaseDiffUtils
import com.mehdisekoba.imdb.utils.extensions.loadImageCircularProgress
import com.mehdisekoba.imdb.utils.extensions.moneySeparating
import com.mehdisekoba.imdb.utils.extensions.toTwoDecimals
import com.mehdisekoba.imdb.utils.view.TicketEdgeTreatment
import javax.inject.Inject

class AdapterGenresMovies
    @Inject
    constructor() : RecyclerView.Adapter<AdapterGenresMovies.ViewHolder>() {
        private lateinit var binding: ItemAllMoviesBinding
        private var items = emptyList<Result>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): ViewHolder {
            binding = ItemAllMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            @SuppressLint("SetTextI18n")
            fun bind(item: Result) {
                binding.apply {
                    // Advanced Shapes
                    val ticketShapePathModel =
                        ShapeAppearanceModel
                            .Builder()
                            .setAllCorners(CornerFamily.ROUNDED, 36f)
                            .setLeftEdge(TicketEdgeTreatment(36f))
                            .setRightEdge(TicketEdgeTreatment(36f))
                            .build()
                    val startShapePathModel =
                        ShapeAppearanceModel
                            .Builder()
                            .setAllCorners(CornerFamily.ROUNDED, 36f)
                            .setLeftEdge(TicketEdgeTreatment(36f))
                            .build()
                    parentCard.shapeAppearanceModel = ticketShapePathModel
                    imgPoster.shapeAppearanceModel = startShapePathModel
                    // text
                    item.let {
                        txtTitle.text = it.title
                        txtFirstDate.text = "${"ReleaseDate :"}  ${it.releaseDate}"
                        val moviePosterURL = TMDB_IMAGE_BASE_URL_W780 + item.posterPath
                        imgPoster.loadImageCircularProgress(moviePosterURL)
                        txtVoteCount.text = it.voteCount?.moneySeparating()
                        val vote = it.voteAverage!!.toTwoDecimals()
                        txtRating.text = vote
                    }
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
