package com.greenfield.sdk.gf_sdk.api

import android.content.ContentValues
import android.util.Log
import com.greenfield.sdk.gf_sdk.config.Config
import com.greenfield.sdk.gf_sdk.services.CreateRequest
import com.greenfield.sdk.gf_sdk.types.VisibilityType
import org.json.JSONArray
import org.json.JSONObject

interface CreateObjectEstimateOpts {
    val ContentLength: Int?
    val ExpectedChecksums: Array<String>?
    val Visibility: VisibilityType?
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
        var visibility = VisibilityType.VISIBILITY_TYPE_UNSPECIFIED
        if (opts.Visibility != null) {
            if (opts.Visibility == VisibilityType.VISIBILITY_TYPE_PUBLIC_READ ||
                opts.Visibility == VisibilityType.VISIBILITY_TYPE_PRIVATE ||
                opts.Visibility == VisibilityType.VISIBILITY_TYPE_INHERIT
            ) {
                visibility = opts.Visibility!!
            }
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
}