package com.mehdisekoba.imdb.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.mehdisekoba.imdb.R
import com.mehdisekoba.imdb.databinding.FragmentProfileBinding
import com.mehdisekoba.imdb.utils.ACTOR_NAME
import com.mehdisekoba.imdb.utils.ACTOR_WOMEN
import com.mehdisekoba.imdb.utils.base.BaseFragment
import com.mehdisekoba.imdb.utils.extensions.loadImageCircularProgress
import com.mehdisekoba.imdb.utils.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (isNetworkAvailable) {
                imgActor.loadImageCircularProgress(ACTOR_WOMEN)
                txtActor.text = ACTOR_NAME
            } else {
                root.showSnackBar(getString(R.string.checkYourNetwork))
            }
        }
    }
}
