package com.greenfield.sdk.gf_sdk.api.bucket

import android.content.ContentValues.TAG
import android.util.Log
import com.greenfield.sdk.gf_sdk.auth.GreenfieldAuth
import com.greenfield.sdk.gf_sdk.auth.GreenfieldGetApproval
import com.greenfield.sdk.gf_sdk.utils.GFUtils
import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class CreateBucket(private val greenfieldAuth: GreenfieldAuth, private val networkConfig: String) {

    @Serializable
    data class MsgCreateBucket(
        val bucket_name: String,
        val charged_read_quota: Long?,
        val creator: String,
        val payment_address: String?,
        val primary_sp_address: String,
        val primary_sp_approval: PrimarySpApproval?,
        val visibility: Int?,
    )

    @Serializable
    data class PrimarySpApproval(
        val expired_height: String,
        val global_virtual_group_family_id: Int,
        val sig: String
    )

    suspend fun createBucket(msgCreateBucket: MsgCreateBucket): String {
        val action = "CreateBucket"
        val unsignedMsg = Json.encodeToString(msgCreateBucket)

        val approvalResult = getApproval(action, unsignedMsg)
        return approvalResult
    }

    suspend fun getApproval(action: String, unsignedMsg: String): String {
        val getApproval = GreenfieldGetApproval(greenfieldAuth, networkConfig)
        val approvalResult = getApproval.getApproval(action, unsignedMsg)
        Log.d(TAG, "createBucket: approvalResult - $approvalResult")

        return approvalResult
    }

    suspend fun getSignedMsg(approvalResult: String): String {
        val signedMsg: MsgCreateBucket = GFUtils.parseJsonFromHex(approvalResult)

        return signedMsg.toString()
    }
}

