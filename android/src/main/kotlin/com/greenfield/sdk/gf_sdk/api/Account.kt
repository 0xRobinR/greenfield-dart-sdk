package com.greenfield.sdk.gf_sdk.api

import com.greenfield.sdk.gf_sdk.config.Config
import com.greenfield.sdk.gf_sdk.services.CreateRequest
import org.json.JSONObject

class Account(address: String) {

    private lateinit var address: String;
    private lateinit var caller: CreateRequest
    init {
        if (address.isEmpty()) throw Exception("address is empty")
        this.address = address;
        this.caller = CreateRequest();
    }

    fun getAccountInfo(): String {
        val reqData = JSONObject()
            .put("address", this.address)
            .toString()
        val response = this.caller.fetchResponse(Config.accountInfo, reqData);

        return response.toString()
    }

    fun getAccountBalance(): String {
        val reqData = JSONObject()
            .put("address", this.address)
            .toString()
        val response = this.caller.fetchResponse(Config.accountBalance, reqData);

        return response.toString()
    }

    fun getUserBuckets(spUrl: String): String {
        val reqData = JSONObject()
            .put("address", this.address)
            .put("spURL", spUrl)
            .toString()
        val response = this.caller.fetchResponse(Config.userBuckets, reqData);

        return response.toString()
    }
}