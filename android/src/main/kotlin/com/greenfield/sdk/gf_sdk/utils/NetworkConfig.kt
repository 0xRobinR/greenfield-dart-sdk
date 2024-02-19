package com.greenfield.sdk.gf_sdk.utils

object NetworkConfig {
    fun getBaseUrl(networkConfig: String) = when (networkConfig) {
        "testnet" -> "https://gnfd-testnet-sp1.bnbchain.org"
        "mainnet" -> "https://greenfield-sp.bnbchain.org"
        else -> throw IllegalArgumentException("Invalid network configuration")
    }
}