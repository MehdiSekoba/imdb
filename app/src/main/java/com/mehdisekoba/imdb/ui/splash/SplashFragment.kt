package com.mehdisekoba.imdb.ui.splash

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mehdisekoba.imdb.BuildConfig
import com.mehdisekoba.imdb.R
import com.mehdisekoba.imdb.data.stored.OnboardingPreferenceManager
import com.mehdisekoba.imdb.databinding.DisconnectInternetBinding
import com.mehdisekoba.imdb.databinding.FragmentSplashBinding
import com.mehdisekoba.imdb.utils.base.BaseFragment
import com.mehdisekoba.imdb.utils.extensions.openSettingDataMobile
import com.mehdisekoba.imdb.utils.extensions.openSettingWifi
import com.mehdisekoba.imdb.utils.network.MyReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@Suppress("DEPRECATION")
class SplashFragment : BaseFragment<FragmentSplashBinding>(), MyReceiver.ConnectivityReceiverListener {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    @Inject
    lateinit var onboardingPreference: OnboardingPreferenceManager

    // other
    private val myReceiver = MyReceiver()

    private var dialog: AlertDialog? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().registerReceiver(
            myReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION),
        )
    }

    // Shows the dialog for internet disconnection
    private fun showDialog() {
        if (dialog == null || !dialog!!.isShowing) {
            val layoutInflater = LayoutInflater.from(requireContext())
            val customDialogView = layoutInflater.inflate(R.layout.disconnect_internet, null)
            val bindingDialog = DisconnectInternetBinding.bind(customDialogView)
            dialog =
                MaterialAlertDialogBuilder(requireContext())
                    .setView(bindingDialog.root)
                    .setCancelable(false)
                    .create()

            bindingDialog.apply {
                turnData.setOnClickListener { requireContext().openSettingDataMobile() }
                turnWifi.setOnClickListener { requireContext().openSettingWifi() }
                imgClose.setOnClickListener { dialog?.dismiss() }
            }

            dialog?.show()
        }
    }

    // Dismisses the dialog
    private fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }

    // Updates the connection UI based on the provided flag
    private fun updateViewConnection(isShown: Boolean) {
        binding.apply {
            txtVersion.isVisible = isShown
            txtAppName.isVisible = isShown
            DotProgressBar.isVisible = isShown
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onNetworkConnectionChanger(isConnected: Boolean) {
        binding.apply {
            if (isConnected) {
                dismissDialog()
                lifecycleScope.launch {
                    updateViewConnection(true)
                    txtAppName.text = getString(R.string.app_name)
                    txtVersion.text = "${getString(R.string.version)} : ${BuildConfig.VERSION_NAME}"
                    // check
                    delay(3000)
                    val firstTime = onboardingPreference.getOnboardingPreference.first()
                    findNavController().popBackStack(R.id.splashFragment, true)
                    if (firstTime == false) {
                        findNavController().navigate(R.id.action_splash_to_onboarding)
                    } else {
                        findNavController().navigate(R.id.action_onboarding_To_Home)
                    }
                }
            } else {
                showDialog()
                updateViewConnection(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MyReceiver.connectivityReceiverListener = this
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(myReceiver)
    }
}
