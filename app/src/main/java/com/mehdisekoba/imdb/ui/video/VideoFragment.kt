package com.mehdisekoba.imdb.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehdisekoba.imdb.R
import com.mehdisekoba.imdb.data.model.movie.moviesDataList
import com.mehdisekoba.imdb.databinding.FragmentVideoBinding
import com.mehdisekoba.imdb.ui.video.adapter.AdapterExplore
import com.mehdisekoba.imdb.utils.base.BaseFragment
import com.mehdisekoba.imdb.utils.extensions.setupShimmerRecyclerview
import com.mehdisekoba.imdb.utils.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentVideoBinding
        get() = FragmentVideoBinding::inflate

    @Inject
    lateinit var adapterExplore: AdapterExplore

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (isNetworkAvailable) {
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.CREATED) {
                        videoList.showShimmerAdapter()
                        delay(3000)
                        adapterExplore.setData(moviesDataList)
                        videoList.setupShimmerRecyclerview(
                            LinearLayoutManager(requireContext()),
                            adapterExplore,
                        )
                    }
                }
            } else {
                root.showSnackBar(getString(R.string.checkYourNetwork))
            }
            // back
            imgBack.setOnClickListener { findNavController().popBackStack() }
        }
    }
}
