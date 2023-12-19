package com.greenfield.sdk.gf_sdk.api

import android.content.ContentValues.TAG
import android.util.Log
import com.greenfield.sdk.gf_sdk.config.Config
import com.greenfield.sdk.gf_sdk.services.CreateRequest
import com.greenfield.sdk.gf_sdk.types.VisibilityType
import org.json.JSONObject

interface CreateBucketOpts {
    val Visibility: VisibilityType
    val PaymentAddress: String?
    var SPAddress: String
}

class Bucket(authKey: String) {

    private lateinit var authKey: String;
    private lateinit var caller: CreateRequest

    companion object {
        fun getBucketInfo(bucketName: String): String {
            val reqData = JSONObject()
                .put("bucketName", bucketName)
                .toString()

            val response = CreateRequest().fetchResponse(Config.bucketInfo, reqData);
            Log.d("gf_sdk", response.toString())

            return response.toString()
        }

        fun getBucketObjects(bucketName: String): String {
            val reqData = JSONObject()
                .put("bucketName", bucketName)
                .toString()

            val response = CreateRequest().fetchResponse(Config.bucketObjects, reqData);
            Log.d("gf_sdk", response.toString())

            return response.toString()
        }
    }

    init {
        if (authKey.isEmpty()) throw Exception("authKey is empty")
        this.authKey = authKey;
        this.caller = CreateRequest();
    }

    fun createBucket(bucketName: String, primaryAddress: String, opts: CreateBucketOpts?): String {
        var visibility = VisibilityType.VISIBILITY_TYPE_UNSPECIFIED
        if (opts?.Visibility != null) {
            if (opts.Visibility == VisibilityType.VISIBILITY_TYPE_PUBLIC_READ ||
                opts.Visibility == VisibilityType.VISIBILITY_TYPE_PRIVATE ||
                opts.Visibility == VisibilityType.VISIBILITY_TYPE_INHERIT
            ) {
                visibility = opts.Visibility
            }
        }

        if (opts?.SPAddress.isNullOrEmpty()) {
            Log.d("gf_sdk", "SPAddress is empty")
            throw Exception("SPAddress is empty")
        }

        if (primaryAddress.isEmpty()) {
            Log.d("gf_sdk", "primaryAddress is empty")
            throw Exception("primaryAddress is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("spAddr", opts?.SPAddress)
            .put("bucketName", bucketName)
            .put("address", primaryAddress)
            .put("visibility", visibility)
            .toString()

        Log.d(TAG, "createBucket: $reqData")

        val response = this.caller.fetchResponse(Config.createBucket, reqData);
        Log.d("gf_sdk", response.toString())

        return response.toString()
    }

    fun requestApproval(
        bucketName: String,
        primaryAddress: String,
        opts: CreateBucketOpts?
    ): String {
        var visibility = VisibilityType.VISIBILITY_TYPE_UNSPECIFIED
        if (opts?.Visibility != null) {
            if (opts.Visibility == VisibilityType.VISIBILITY_TYPE_PUBLIC_READ ||
                opts.Visibility == VisibilityType.VISIBILITY_TYPE_PRIVATE ||
                opts.Visibility == VisibilityType.VISIBILITY_TYPE_INHERIT
            ) {
                visibility = opts.Visibility
            }
        }

        if (opts?.SPAddress.isNullOrEmpty()) {
            Log.d("gf_sdk", "SPAddress is empty")
            throw Exception("SPAddress is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("spAddr", opts?.SPAddress)
            .put("bucketName", bucketName)
            .put("address", primaryAddress)
            .put("visibility", visibility)
            .toString()

        Log.d(TAG, "requestApproval: $reqData")

        val response = this.caller.fetchResponse(Config.requestApproval, reqData);
        Log.d("gf_sdk", response.toString())

        return response.toString()
    }
}