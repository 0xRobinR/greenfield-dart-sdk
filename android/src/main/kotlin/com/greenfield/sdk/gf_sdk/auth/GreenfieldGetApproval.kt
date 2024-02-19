package com.greenfield.sdk.gf_sdk.auth

import android.content.ContentValues.TAG
import android.util.Log
import com.greenfield.sdk.gf_sdk.utils.GFUtils
import com.greenfield.sdk.gf_sdk.utils.NetworkConfig.getBaseUrl
import com.greenfield.sdk.gf_sdk.utils.TimeUtils
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject

class GreenfieldGetApproval(
    private val greenfieldAuth: GreenfieldAuth,
    private val networkConfig: String
) {

    private val client = OkHttpClient()

    private fun getSPUrlByAddress(spAddress: String): String {
        val baseUrl = getBaseUrl(networkConfig)
        return "$baseUrl/greenfield/admin/v1/sp/$spAddress"
    }

    //    fun getApproval(action: String, unsignedMsg: String): String {
//        val baseUrl = getBaseUrl()
//        val urlString = "$baseUrl/greenfield/admin/v1/get-approval?action=$action"
//        val url = URL(urlString)
//        val connection = url.openConnection() as HttpURLConnection
//
//        Log.d(TAG, "getApproval: $urlString")
//
//        try {
//            connection.requestMethod = "GET"
//            connection.setRequestProperty("Content-Type", "application/json")
//            connection.setRequestProperty("X-Gnfd-Unsigned-Msg", unsignedMsg)
//            connection.setRequestProperty(
//                "X-Gnfd-Expiry-Timestamp",
//                TimeUtils.generateExpiryTimestamp()
//            )
//
//            val authorizationHeader = greenfieldAuth.generateAuthorizationHeader(
//                "GET",
//                "/greenfield/admin/v1/get-approval",
//                "action=$action",
//                "X-Gnfd-Unsigned-Msg: $unsignedMsg",
//                "x-gnfd-unsigned-msg"
//            )
//            connection.setRequestProperty("Authorization", authorizationHeader)
//
//            Log.d(TAG, "getApproval: authorizationHeader - $authorizationHeader")
//
//            connection.connect()
//
//            val responseCode = connection.responseCode
//            Log.d(TAG, "getApproval: responseCode - $responseCode ${connection.responseMessage}")
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                Log.d(TAG, "getApproval: HTTP_OK")
//                val signedMsg = connection.getHeaderField("X-Gnfd-Signed-Msg")
//                Log.d(TAG, "getApproval: signedMsg - $signedMsg")
//                val jsonResponse = JSONObject()
//                jsonResponse.put("signedMsg", signedMsg)
//                return jsonResponse.toString()
//            } else {
//                throw Exception("Failed to get approval. HTTP error code: $responseCode")
//            }
//        } catch (e: Exception) {
//            Log.e("gf_sdk", "Error in createBucket", e)
//            return e.toString()
//        }
//    }
    @Throws(IOException::class)
    suspend fun getApproval(action: String, unsignedMsg: String): String =
        withContext(Dispatchers.IO) {
            val baseUrl = getBaseUrl(networkConfig)
            val url = "$baseUrl/greenfield/admin/v1/get-approval?action=$action"

            val unsignedMsgInHex = GFUtils.convertMessageToHex(unsignedMsg)

            val authorization = greenfieldAuth.generateAuthorizationHeader(
                "GET",
                "/greenfield/admin/v1/get-approval",
                "action=$action",
                "x-gnfd-unsigned-msg:${unsignedMsgInHex.toLowerCase()}",
                "x-gnfd-unsigned-msg"
            )

            val request = Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Gnfd-Unsigned-Msg", unsignedMsgInHex)
                .addHeader("X-Gnfd-Expiry-Timestamp", TimeUtils.generateExpiryTimestamp())
                .addHeader(
                    "Authorization", authorization
                )
                .build()

            try {
                Log.d(TAG, "getApproval: request - $request")
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response ${response.message} ${response.body.string()}")

                    val signedMsg = response.header("X-Gnfd-Signed-Msg")
                    val jsonResponse = JSONObject()
                    jsonResponse.put("signedMsg", signedMsg)
                    return@withContext jsonResponse.toString()
                }
            } catch (e: Exception) {
                Log.e("gf_sdk", "Error in createBucket", e)
                return@withContext e.toString()
            }

        }


}
