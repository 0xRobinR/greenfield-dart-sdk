package com.greenfield.sdk.gf_sdk.services

import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class CreateRequest {
    fun fetchResponse(url: String, json: String): String {
        val client =
            OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
                .readTimeout(30, TimeUnit.SECONDS)    // Set read timeout
                .writeTimeout(30, TimeUnit.SECONDS)   // Set write timeout
                .build()

        val jsonMediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = json.toRequestBody(jsonMediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                return response.body.string()
            }
        } catch (e: Exception) {
            Log.d(TAG, "fetchPostError: ${e.message}")
            "{\"error\": \"${e.message}\"}"
        }
    }

    fun fetchStatic(url: String): String {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                return response.body.string()
            }
        } catch (e: Exception) {
            print("fetchError: ${e.message}")
            e.message.toString()
        }
    }
}