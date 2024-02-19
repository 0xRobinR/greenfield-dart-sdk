package com.greenfield.sdk.gf_sdk.utils

import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.bouncycastle.util.encoders.Hex


object GFUtils {

    val json = Json { ignoreUnknownKeys = true }
    fun convertMessageToHex(msg: String): String {
        // Convert the message object to a JSON string
        val jsonString = msg

        // Encode the JSON string to UTF-8 bytes
        val bytes = jsonString.toByteArray(Charsets.UTF_8)

        // Convert bytes to hex string
        return bytesToHex(bytes)
    }

    fun bytesToHex(bytes: ByteArray): String {
        val hexArray = "0123456789ABCDEF".toCharArray()
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = hexArray[v ushr 4]
            hexChars[i * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    fun parseSignedMessage(body: String): Any {
        // Convert hex string to byte array
        val bytes = hexToBytes(body)

        // Convert byte array to UTF-8 string
        val utf8String = bytesToUtf8(bytes)

        // Parse the JSON string
        return Gson().fromJson(utf8String, Any::class.java)
    }

    fun hexToBytes(hexString: String): ByteArray {
        return Hex.decode(hexString)
    }

    fun bytesToUtf8(bytes: ByteArray): String {
        return bytes.toString(Charsets.UTF_8)
    }

    inline fun <reified T> parseJsonFromHex(hexString: String): T {
        val bytes = hexToBytes(hexString)
        val jsonString = bytesToUtf8(bytes)
        return json.decodeFromString(jsonString)
    }
}