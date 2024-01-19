package com.greenfield.sdk.gf_sdk.api.`object`

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.greenfield.sdk.gf_sdk.auth.GreenfieldAuth
import com.greenfield.sdk.gf_sdk.auth.GreenfieldGetApproval
import com.greenfield.sdk.gf_sdk.utils.NetworkConfig
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class CreateObject(private val greenfieldAuth: GreenfieldAuth, private val networkConfig: String) {

    data class MsgCreateObject(
        val creator: String,
        val bucketName: String,
        val objectName: String,
        val payloadSize: Int,
        val visibility: Int,
        val contentType: String,
        val primarySpApproval: String,
        val expectChecksums: ByteArray,
        val redundancyType: Int,
        val expectSecondarySpAddresses: List<String>
    )

    fun createObject(msgCreateObject: MsgCreateObject): String {
        val getApproval = GreenfieldGetApproval(greenfieldAuth, networkConfig)
        val action = "CreateObject"
        val unsignedMsg = Gson().toJson(msgCreateObject)

        // Use GetApproval to get the signed message
        val signedMsg = getApproval.getApproval(action, unsignedMsg)

        // Now, create the object using the signed message
        val baseUrl = NetworkConfig.getBaseUrl(networkConfig)
        val urlString = "$baseUrl/greenfield/storage/v1/object"
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("X-Gnfd-Unsigned-Msg", unsignedMsg)
            connection.setRequestProperty("X-Gnfd-Signed-Msg", signedMsg)

            val postData = Gson().toJson(msgCreateObject).toByteArray(StandardCharsets.UTF_8)
            connection.doOutput = true
            connection.outputStream.write(postData)

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val jsonResponse = JsonObject()
                jsonResponse.addProperty("message", "Object Approved Successfully")
                jsonResponse.addProperty(
                    "X-Gnfd-Signed-Msg",
                    connection.getHeaderField("X-Gnfd-Signed-Msg")
                )
                return Gson().toJson(jsonResponse)
            } else {
                throw Exception("Failed to create object. HTTP error code: $responseCode")
            }
        } finally {
            connection.disconnect()
        }
    }
}
