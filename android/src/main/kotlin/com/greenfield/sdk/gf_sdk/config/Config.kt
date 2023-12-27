package com.greenfield.sdk.gf_sdk.config

class Config {
    companion object {
        val baseUrl = "https://gf-node.vercel.app";

        val requestApproval = "$baseUrl/getCreateBucketEstimate"
        val createBucket = "$baseUrl/createBucket"

        val accountInfo = "$baseUrl/accountInfo"
        val accountBalance = "$baseUrl/accountBalance"
        val userBuckets = "$baseUrl/userBuckets"
        val bucketInfo = "$baseUrl/bucketInfo"
        val bucketObjects = "$baseUrl/bucketObjects"
        val createObject = "$baseUrl/createObject"
        val deleteObject = "$baseUrl/deleteObject"
        val cancelObject = "$baseUrl/cancelObject"
        val createObjectEstimate = "$baseUrl/getCreateObjectEstimate"
        val createFolder = "$baseUrl/createFolder"
        val uploadObject = "$baseUrl/uploadObject"
        val updateObject = "$baseUrl/updateObject"

        val getBlock = "$baseUrl/getBlock"
        val stats = "$baseUrl/stats"
        val storageProviders = "$baseUrl/storageProviders"

    }
}