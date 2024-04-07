package com.mehdisekoba.imdb.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class MyReceiver : BroadcastReceiver() {
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanger(isConnected: Boolean)
    }

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        connectivityReceiverListener?.onNetworkConnectionChanger(isConnectedOrConnecting(context!!))
    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}
