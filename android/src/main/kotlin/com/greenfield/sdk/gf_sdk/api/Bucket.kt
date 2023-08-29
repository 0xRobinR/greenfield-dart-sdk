package com.greenfield.sdk.gf_sdk.api

import android.util.Log
import com.greenfield.sdk.gf_sdk.types.VisibilityType

interface CreateBucketOpts {
    val Visibility: VisibilityType
    val PaymentAddress: String
    val ChargedQuota: Long
}
class Bucket {
    fun createBucket(bucketName: String, primaryAddress: String, opts: CreateBucketOpts?) {
        var visibility = VisibilityType.VISIBILITY_TYPE_UNSPECIFIED
        if (opts?.Visibility != null) {
            if ( opts.Visibility == VisibilityType.VISIBILITY_TYPE_PUBLIC_READ ||
                opts.Visibility == VisibilityType.VISIBILITY_TYPE_PRIVATE ||
                opts.Visibility == VisibilityType.VISIBILITY_TYPE_INHERIT) {
                visibility = opts.Visibility
            }
        }

        Log.d("gf_sdk",visibility.toString())
    }
}