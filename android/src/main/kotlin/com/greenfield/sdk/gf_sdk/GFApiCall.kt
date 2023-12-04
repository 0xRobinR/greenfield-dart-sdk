package com.greenfield.sdk.gf_sdk

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


class GFApiCall {

    final var baseUrl = "https://gnfd-testnet-fullnode-tendermint-ap.bnbchain.org/";

    suspend fun makeNetworkCall() {
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("${baseUrl}status")
                .build()

            client.newCall(request).execute().use { response ->
                Log.d("gf_sdk", response.body!!.string())
            }
        }
    }
}