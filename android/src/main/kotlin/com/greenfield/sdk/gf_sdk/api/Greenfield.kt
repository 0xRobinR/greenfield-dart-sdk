package com.greenfield.sdk.gf_sdk.api

import com.greenfield.sdk.gf_sdk.config.Config
import com.greenfield.sdk.gf_sdk.services.CreateRequest

class Greenfield {
    companion object {
        fun getSDKVersion(): String {
            return "0.0.1"
        }

        fun getStats(): String {
            val response = CreateRequest().fetchStatic(Config.stats);
            return response.toString()
        }

        fun getStorageProviders(): String {
            val response = CreateRequest().fetchStatic(Config.storageProviders);
            return response.toString()
        }

        fun getBlock(): String {
            val response = CreateRequest().fetchStatic(Config.getBlock);
            return response.toString()
        }
    }
}