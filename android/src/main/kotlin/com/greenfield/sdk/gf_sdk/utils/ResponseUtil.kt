package com.greenfield.sdk.gf_sdk.utils

import com.google.gson.Gson
import com.google.protobuf.util.JsonFormat
import com.greenfield.sdk.gf_sdk.CreateBucketApprovalSignedMsgKt
import com.greenfield.sdk.gf_sdk.CreateObjectApprovalSignedMsgKt

object ResponseUtil {
    fun decodeSignedMsgForCreateBucket(signedMsgHex: String): String {
        return decodeSignedMsg(signedMsgHex, CreateBucketApprovalSignedMsgKt.getDefaultInstance())
    }

    fun decodeSignedMsgForCreateObject(signedMsgHex: String): String {
        return decodeSignedMsg(signedMsgHex, CreateObjectApprovalSignedMsgKt.getDefaultInstance())
    }

    private fun decodeSignedMsg(
        signedMsgHex: String,
        messageType: Class<out com.google.protobuf.Message>
    ): String {
        val bytes = signedMsgHex.hexStringToByteArray()
        val messageBuilder =
            messageType.getMethod("newBuilder").invoke(null) as com.google.protobuf.Message.Builder
        JsonFormat.parser().merge(String(bytes, Charsets.UTF_8), messageBuilder)
        return Gson().toJson(messageBuilder.build())
    }

    private fun String.hexStringToByteArray(): ByteArray {
        val len = length
        val data = ByteArray(len / 2)
        for (i in 0 until len step 2) {
            data[i / 2] =
                ((Character.digit(get(i), 16) shl 4) + Character.digit(get(i + 1), 16)).toByte()
        }
        return data
    }
}
