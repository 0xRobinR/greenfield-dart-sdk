package com.greenfield.sdk.gf_sdk.api

import com.greenfield.sdk.gf_sdk.config.Config
import com.greenfield.sdk.gf_sdk.services.CreateRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

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
            throw Exception("bucket name is empty")
        }

        if (opts.ObjectName.isNullOrEmpty()) {
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


        val response = this.caller.fetchResponse(Config.createObjectEstimate, reqData);

        return response.toString()
    }

    fun createObject(opts: CreateObjectEstimateOpts): String {
        var visibility = "VISIBILITY_TYPE_PRIVATE"
        if (opts.Visibility != null) {
            visibility = opts.Visibility!!
        }

        if (opts.BucketName.isNullOrEmpty()) {
            throw Exception("bucket name is empty")
        }

        if (opts.ObjectName.isNullOrEmpty()) {
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


        val response = this.caller.fetchResponse(Config.createObject, reqData);

        return response.toString()
    }

    fun deleteObject(bucketName: String?, objectName: String?, creator: String?): String {
        if (bucketName.isNullOrEmpty()) {
            throw Exception("bucket name is empty")
        }

        if (objectName.isNullOrEmpty()) {
            throw Exception("object name is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("creator", creator)
            .put("bucketName", bucketName)
            .put("objectName", objectName)
            .toString()


        val response = this.caller.fetchResponse(Config.deleteObject, reqData);

        return response.toString()
    }

    fun cancelObject(bucketName: String?, objectName: String?, creator: String?): String {
        if (bucketName.isNullOrEmpty()) {
            throw Exception("bucket name is empty")
        }

        if (objectName.isNullOrEmpty()) {
            throw Exception("object name is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("creator", creator)
            .put("bucketName", bucketName)
            .put("objectName", objectName)
            .toString()


        val response = this.caller.fetchResponse(Config.cancelObject, reqData);

        return response.toString()
    }

    fun updateObject(
        bucketName: String?,
        objectName: String?,
        creator: String?,
        visibility: String?
    ): String {
        if (bucketName.isNullOrEmpty()) {
            throw Exception("bucket name is empty")
        }

        if (objectName.isNullOrEmpty()) {
            throw Exception("object name is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("creator", creator)
            .put("bucketName", bucketName)
            .put("objectName", objectName)
            .put("visibility", visibility)
            .toString()


        val response = this.caller.fetchResponse(Config.updateObject, reqData);

        return response.toString()
    }

    fun createFolder(opts: CreateObjectEstimateOpts): String {
        if (opts.BucketName.isNullOrEmpty()) {
            throw Exception("bucket name is empty")
        }

        if (opts.ObjectName.isNullOrEmpty()) {
            throw Exception("object name is empty")
        }

        val reqData = JSONObject()
            .put("auth", this.authKey)
            .put("creator", opts.Creator)
            .put("bucketName", opts.BucketName)
            .put("objectName", opts.ObjectName)
            .toString()


        val response = this.caller.fetchResponse(Config.createFolder, reqData);

        return response.toString()
    }

    fun uploadFile(
        opts: CreateObjectEstimateOpts, txHash: String,
        file: String
    ): String {
        if (opts.BucketName.isNullOrEmpty()) {
            throw Exception("bucket name is empty")
        }

        if (opts.ObjectName.isNullOrEmpty()) {
            throw Exception("object name is empty")
        }

        if (txHash.isEmpty()) {
            throw Exception("txHash is empty")
        }

        if (file.isEmpty()) {
            throw Exception("file is empty")
        }

        val filePost = File(file)

        val reqData = opts.Creator?.let {
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("auth", authKey)
                .addFormDataPart("creator", it)
                .addFormDataPart("bucketName", opts.BucketName!!)
                .addFormDataPart("objectName", opts.ObjectName!!)
                .addFormDataPart("txHash", txHash)
                .addFormDataPart(
                    "file", filePost.name,
                    filePost.asRequestBody("application/octet-stream".toMediaTypeOrNull())
                )
                .build()
        }


        val response = reqData?.let { this.caller.fetchResponseFile(Config.uploadObject, it) };

        return response.toString()
    }
}
