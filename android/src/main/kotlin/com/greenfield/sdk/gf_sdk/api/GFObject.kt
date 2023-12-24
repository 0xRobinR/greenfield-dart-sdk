package com.greenfield.sdk.gf_sdk.api

import android.content.ContentValues
import android.util.Log
import com.greenfield.sdk.gf_sdk.config.Config
import com.greenfield.sdk.gf_sdk.services.CreateRequest
import org.json.JSONArray
import org.json.JSONObject

interface CreateObjectEstimateOpts {
    val ContentLength: Int?
    val ExpectedChecksums: Array<String>?
    val Visibility: String?
    val FileType: String?
    val ObjectName: String?
    val BucketName: String?
    val Creator: String?
}

class GFObject(authKey: String) {
    private lateinit var authKey: String;
    private lateinit var caller: CreateRequest

    companion object {

    }

    init {
        if (authKey.isEmpty()) throw Exception("authKey is empty")
        this.authKey = authKey;
        this.caller = CreateRequest();
    }

    fun createObjectEstimate(opts: CreateObjectEstimateOpts): String {
        var visibility = "VISIBILITY_TYPE_PRIVATE"
        if (opts.Visibility != null) {
            visibility = opts.Visibility!!
        }

        if (opts.BucketName.isNullOrEmpty()) {
            Log.d("gf_sdk", "bucket name is empty")
            throw Exception("bucket name is empty")
        }

        if (opts.ObjectName.isNullOrEmpty()) {
            Log.d("gf_sdk", "object name is empty")
            throw Exception("object name is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("creator", opts.Creator)
            .put("bucketName", opts.BucketName)
            .put("objectName", opts.ObjectName)
            .put("fileType", opts.FileType)
            .put("expectedChecksums", JSONArray(opts.ExpectedChecksums).toString())
            .put("contentLength", opts.ContentLength)
            .put("visibility", visibility)
            .toString()

        Log.d(ContentValues.TAG, "create object estimate: $reqData")

        val response = this.caller.fetchResponse(Config.createObjectEstimate, reqData);
        Log.d("gf_sdk", response.toString())

        return response.toString()
    }

    fun createObject(opts: CreateObjectEstimateOpts): String {
        var visibility = "VISIBILITY_TYPE_PRIVATE"
        if (opts.Visibility != null) {
            visibility = opts.Visibility!!
        }

        if (opts.BucketName.isNullOrEmpty()) {
            Log.d("gf_sdk", "bucket name is empty")
            throw Exception("bucket name is empty")
        }

        if (opts.ObjectName.isNullOrEmpty()) {
            Log.d("gf_sdk", "object name is empty")
            throw Exception("object name is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("creator", opts.Creator)
            .put("bucketName", opts.BucketName)
            .put("objectName", opts.ObjectName)
            .put("fileType", opts.FileType)
            .put("expectedChecksums", JSONArray(opts.ExpectedChecksums).toString())
            .put("contentLength", opts.ContentLength)
            .put("visibility", visibility)
            .toString()

        Log.d(ContentValues.TAG, "create object estimate: $reqData")

        val response = this.caller.fetchResponse(Config.createObject, reqData);
        Log.d("gf_sdk", response.toString())

        return response.toString()
    }

    fun createFolder(opts: CreateObjectEstimateOpts): String {
        if (opts.BucketName.isNullOrEmpty()) {
            Log.d("gf_sdk", "bucket name is empty")
            throw Exception("bucket name is empty")
        }

        if (opts.ObjectName.isNullOrEmpty()) {
            Log.d("gf_sdk", "object name is empty")
            throw Exception("object name is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("creator", opts.Creator)
            .put("bucketName", opts.BucketName)
            .put("objectName", opts.ObjectName)
            .toString()

        Log.d(ContentValues.TAG, "create folder: $reqData")

        val response = this.caller.fetchResponse(Config.createFolder, reqData);
        Log.d("gf_sdk", response.toString())

        return response.toString()
    }
}
