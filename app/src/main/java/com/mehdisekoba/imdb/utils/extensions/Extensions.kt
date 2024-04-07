package com.mehdisekoba.imdb.utils.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.request.CachePolicy
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.mehdisekoba.imdb.R
import com.mikelau.shimmerrecyclerviewx.ShimmerRecyclerViewX
import java.text.DecimalFormat

fun FragmentActivity.setStatusBarIconsColor(isDark: Boolean) {
    this.window.apply {
        WindowCompat.getInsetsController(this, this.decorView).apply {
            isAppearanceLightStatusBars = isDark
        }
    }
}

fun setupLoading(
    isShownLoading: Boolean,
    shimmer: ShimmerRecyclerViewX,
) {
    shimmer.apply {
        if (isShownLoading) showShimmerAdapter() else hideShimmerAdapter()
    }
}

fun ShimmerRecyclerViewX.setupShimmerRecyclerview(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>,
) {
    this.apply {
        setAdapter(adapter)
        setLayoutManager(layoutManager)
    }
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun ImageView.loadImage(url: String) {
    this.load(url) {
        crossfade(true)
        crossfade(500)
        diskCachePolicy(CachePolicy.ENABLED)
        error(R.drawable.placeholder)
    }
}

fun ImageView.loadImageCircularProgress(url: String) {
    val progressDrawable =
        CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            setColorSchemeColors(ContextCompat.getColor(context, R.color.Gold))
            start()
        }
    this.load(url) {
        placeholder(progressDrawable)
    }
}

fun Context.openSettingWifi() {
    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
    startActivity(intent)
}

fun Context.openSettingDataMobile() {
    val intent = Intent(Settings.ACTION_SETTINGS)
    startActivity(intent)
}

fun MaterialAlertDialogBuilder.createAndShow() {
    val dialog = create()
    dialog.show()
}

fun Context.alert(dialogBuilder: MaterialAlertDialogBuilder.() -> Unit) {
    MaterialAlertDialogBuilder(this)
        .apply {
            setCancelable(false)
            dialogBuilder()
        }
        .createAndShow()
}

fun LottieAnimationView.isVisible(
    isShownLoading: Boolean,
    container: View,
) {
    if (isShownLoading) {
        this.isVisible = true
        container.isVisible = false
    } else {
        this.isVisible = false
        container.isVisible = true
    }
}

fun Double.toTwoDecimals(): String {
    return String.format("%.1f", this)
}

fun Int?.toHoursAndMinutesString(): String {
    return this?.let { runtime ->
        val hours = runtime / 60
        val minutes = runtime % 60
        when {
            hours > 0 && minutes > 0 -> "$hours h $minutes min"
            hours > 0 -> "$hours h"
            else -> "$minutes min"
        }
    } ?: "N/A"
}

fun ViewGroup.disableWebViewsBackground() {
    children.forEach { child ->
        when (child) {
            is WebView -> child.setBackgroundColor(Color.TRANSPARENT)
            is ViewGroup -> child.disableWebViewsBackground()
        }
    }
}

fun Int.moneySeparating(): String {
    return "${DecimalFormat("#,###.##").format(this)} "
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? =
    when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else ->
            @Suppress("DEPRECATION")
            getParcelable(key)
                as? T
    }
