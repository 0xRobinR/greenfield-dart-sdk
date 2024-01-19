package com.greenfield.sdk.gf_sdk.auth

import java.net.HttpURLConnection
import java.net.URL

class GreenfieldGetApproval(
    private val greenfieldAuth: GreenfieldAuth,
    private val networkConfig: String
) {
    private fun getBaseUrl() = when (networkConfig) {
        "testnet" -> "https://gnfd-testnet-sp.bnbchain.org"
        "mainnet" -> "https://greenfield-sp.bnbchain.org"
        else -> throw IllegalArgumentException("Invalid network configuration")
    }

    fun getApproval(action: String, unsignedMsg: String): String {
        val baseUrl = getBaseUrl()
        val urlString = "$baseUrl/greenfield/admin/v1/get-approval?action=$action"
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("X-Gnfd-Unsigned-Msg", unsignedMsg)

            val authorizationHeader = greenfieldAuth.generateAuthorizationHeader(
                "GET",
                "/greenfield/admin/v1/get-approval",
                "action=$action",
                "X-Gnfd-Unsigned-Msg: $unsignedMsg",
                "x-gnfd-unsigned-msg"
            )
            connection.setRequestProperty("Authorization", authorizationHeader)

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val signedMsg = connection.getHeaderField("X-Gnfd-Signed-Msg")
                return signedMsg ?: throw Exception("Signed message not found in the response")
            } else {
                throw Exception("Failed to get approval. HTTP error code: $responseCode")
            }
        } finally {
            connection.disconnect()
        }
    }
}
