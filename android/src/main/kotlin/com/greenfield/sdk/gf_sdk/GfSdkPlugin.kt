package com.greenfield.sdk.gf_sdk

import android.content.ContentValues.TAG
import android.util.Log
import com.greenfield.sdk.gf_sdk.api.Account
import com.greenfield.sdk.gf_sdk.api.Bucket
import com.greenfield.sdk.gf_sdk.api.CreateBucketOpts
import com.greenfield.sdk.gf_sdk.api.CreateObjectEstimateOpts
import com.greenfield.sdk.gf_sdk.api.GFObject
import com.greenfield.sdk.gf_sdk.api.Greenfield
import com.greenfield.sdk.gf_sdk.api.bucket.CreateBucket
import com.greenfield.sdk.gf_sdk.auth.GreenfieldAuth
import com.greenfield.sdk.gf_sdk.hashencoder.Hashencoder
import com.greenfield.sdk.gf_sdk.types.VisibilityType
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** GfSdkPlugin */
class GfSdkPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private val encoder = Hashencoder()

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "gf_sdk")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getPlatformVersion" -> {
                Log.d("gf_sdk", "fetching tendermint status")
                CoroutineScope(Dispatchers.IO).launch {
                    GFApiCall().makeNetworkCall().run { Log.d("gf_sdk", this.toString()) }
                    result.success("Android ${android.os.Build.VERSION.RELEASE}")
                }
            }

            "createBucketGetApproval" -> {
                val args = call.arguments as? Map<String, Any> ?: run {
                    result.error("INVALID_ARGUMENTS", "Arguments are not provided correctly", null)
                    return
                }

                val privateKey = args["privateKey"] as? String ?: run {
                    result.error("INVALID_ARGUMENTS", "Private key is missing", null)
                    return
                }
                val networkConfig = args["networkConfig"] as? String ?: run {
                    result.error("INVALID_ARGUMENTS", "Network configuration is missing", null)
                    return
                }

                Log.d("gf_sdk", "greenfield auth onMethodCall: $privateKey");

                val greenfieldAuth = GreenfieldAuth(privateKey)

                val creator = args["creator"] as? String ?: ""
                val bucketName = args["bucketName"] as? String ?: ""
                val visibility = args["visibility"] as? Int ?: 0
                val primarySpAddress = args["primarySpAddress"] as? String ?: ""

                Log.d("gf_sdk", "onMethodCall: $creator $bucketName $visibility $primarySpAddress");

                try {
                    val createBucket = CreateBucket(greenfieldAuth, networkConfig)
                    val primary_sp_approval = CreateBucket.PrimarySpApproval(
                        expired_height = "0",
                        global_virtual_group_family_id = 0,
                        sig = ""
                    )

                    val msgCreateBucket = CreateBucket.MsgCreateBucket(
                        creator = creator,
                        bucket_name = bucketName,
                        visibility = 1,
                        payment_address = creator,
                        primary_sp_address = primarySpAddress,
                        primary_sp_approval = primary_sp_approval,
                        charged_read_quota = 0
                    )

                    Log.d(TAG, "msgcreateBucket onMethodCall: $msgCreateBucket")
                    CoroutineScope(Dispatchers.IO).launch {
                        val result_inner = createBucket.createBucket(msgCreateBucket)

                        Log.d(TAG, "onMethodCall: result $result_inner")
                        result.success(result_inner)
                    }
                } catch (e: Exception) {
                    result.error("CREATE_BUCKET_ERROR", e.message, null)
                }
            }

            "getApproval" -> {
                Log.d("gf_sdk", "getting approval of bucket")
                CoroutineScope(Dispatchers.IO).launch {
                    val b = Bucket(call.argument<String>("authKey")!!)
                    val opts = object : CreateBucketOpts {
                        override val Visibility = VisibilityType.VISIBILITY_TYPE_PUBLIC_READ
                        override val PaymentAddress = call.argument<String>("paymentAddress")
                        override var SPAddress = call.argument<String>("spAddress")!!
                    }
                    val response = b.requestApproval(
                        call.argument<String>("bucketName")!!,
                        call.argument<String>("primaryAddress")!!,
                        opts
                    )
                    result.success(response)
                }
            }

            "createBucket" -> {
                Log.d("gf_sdk", "creating bucket")
                CoroutineScope(Dispatchers.IO).launch {
                    val b = Bucket(call.argument<String>("authKey")!!)
                    val opts = object : CreateBucketOpts {
                        override val Visibility = VisibilityType.VISIBILITY_TYPE_PUBLIC_READ
                        override val PaymentAddress = call.argument<String>("paymentAddress")
                        override var SPAddress: String = call.argument<String>("spAddress")!!
                    }
                    val response = b.createBucket(
                        call.argument<String>("bucketName")!!,
                        call.argument<String>("primaryAddress")!!,
                        opts
                    )
                    result.success(response)
                }
            }

            "getStats" -> {
                Log.d("gf_sdk", "getting stats")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Greenfield.getStats()
                    result.success(response)
                }
            }

            "getStorageProviders" -> {
                Log.d("gf_sdk", "getting storage providers")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Greenfield.getStorageProviders()
                    result.success(response)
                }
            }

            "getBlock" -> {
                Log.d("gf_sdk", "getting block")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Greenfield.getBlock()
                    result.success(response)
                }
            }

            "getAccountInfo" -> {
                Log.d("gf_sdk", "getting account info")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Account(call.argument<String>("address")!!).getAccountInfo()
                    result.success(response)
                }
            }

            "getAccountBalance" -> {
                Log.d("gf_sdk", "getting account balance")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Account(call.argument<String>("address")!!).getAccountBalance()
                    result.success(response)
                }
            }

            "getBucketInfo" -> {
                Log.d("gf_sdk", "getting bucket info")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Bucket.getBucketInfo(call.argument<String>("bucketName")!!)
                    result.success(response)
                }
            }

            "getBucketObjects" -> {
                Log.d("gf_sdk", "getting bucket objects")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Bucket.getBucketObjects(call.argument<String>("bucketName")!!)
                    result.success(response)
                }
            }

//            "createObject" -> {
//                Log.d("gf_sdk", "creating object")
//                CoroutineScope(Dispatchers.IO).launch {
//                    val response = Object(call.argument<String>("authKey")!!).createObjectEstimate(
//                        call.argument<String>("objectName")!!,
//                        call.argument<String>("objectData")!!,
//                        call.argument<String>("spUrl")!!
//                    )
//                    result.success(response)
//                }
//            }

            "createObjectEstimate" -> {
                Log.d("gf_sdk", "creating object estimate")

                CoroutineScope(Dispatchers.IO).launch {
                    val expectedChecksums = call.argument<List<String>>("expectedChecksums")
                    val checksumsArray: Array<String> =
                        expectedChecksums?.toTypedArray() ?: arrayOf()
                    Log.d("gf_sdk", "onMethodCall: ${call.argument<String>("visibility")}")
                    val response =
                        GFObject(call.argument<String>("authKey")!!).createObjectEstimate(
                            object : CreateObjectEstimateOpts {
                                override val ContentLength = call.argument<Int>("contentLength")
                                override val ExpectedChecksums =
                                    checksumsArray
                                override val FileType = call.argument<String>("fileType")
                                override val ObjectName = call.argument<String>("objectName")
                                override val BucketName = call.argument<String>("bucketName")
                                override val Creator = call.argument<String>("creator")
                                override val Visibility =
                                    call.argument<String>("visibility")
                            }
                        )
                    result.success(response)
                }
            }

            "createObject" -> {
                Log.d("gf_sdk", "creating object call")

                CoroutineScope(Dispatchers.IO).launch {
                    val expectedChecksums = call.argument<List<String>>("expectedChecksums")
                    val checksumsArray: Array<String> =
                        expectedChecksums?.toTypedArray() ?: arrayOf()
                    Log.d("gf_sdk", "onMethodCall: ${call.argument<String>("visibility")}")
                    val response =
                        GFObject(call.argument<String>("authKey")!!).createObject(
                            object : CreateObjectEstimateOpts {
                                override val ContentLength = call.argument<Int>("contentLength")
                                override val ExpectedChecksums =
                                    checksumsArray
                                override val FileType = call.argument<String>("fileType")
                                override val ObjectName = call.argument<String>("objectName")
                                override val BucketName = call.argument<String>("bucketName")
                                override val Creator = call.argument<String>("creator")
                                override val Visibility =
                                    call.argument<String>("visibility")
                            }
                        )
                    result.success(response)
                }
            }

            "deleteObject" -> {
                Log.d("gf_sdk", "deleting object call")

                CoroutineScope(Dispatchers.IO).launch {
                    val response =
                        GFObject(call.argument<String>("authKey")!!).deleteObject(
                            call.argument("bucketName"),
                            call.argument("objectName"),
                            call.argument("creator")
                        )
                    result.success(response)
                }
            }

            "cancelObject" -> {
                Log.d("gf_sdk", "cancelling object call")

                CoroutineScope(Dispatchers.IO).launch {
                    val response =
                        GFObject(call.argument<String>("authKey")!!).cancelObject(
                            call.argument("bucketName"),
                            call.argument("objectName"),
                            call.argument("creator")
                        )
                    result.success(response)
                }
            }

            "updateObject" -> {
                Log.d("gf_sdk", "updating object call")

                CoroutineScope(Dispatchers.IO).launch {
                    val response =
                        GFObject(call.argument<String>("authKey")!!).updateObject(
                            call.argument("bucketName"),
                            call.argument("objectName"),
                            call.argument("creator"),
                            call.argument("visibility"),
                        )
                    result.success(response)
                }
            }

            "createFolder" -> {
                Log.d("gf_sdk", "creating object call")

                CoroutineScope(Dispatchers.IO).launch {
                    val response =
                        GFObject(call.argument<String>("authKey")!!).createFolder(
                            object : CreateObjectEstimateOpts {
                                override val ContentLength = 0
                                override val ExpectedChecksums = arrayOf<String>()
                                override val Visibility = ""
                                override val FileType = ""
                                override val ObjectName = call.argument<String>("objectName")
                                override val BucketName = call.argument<String>("bucketName")
                                override val Creator = call.argument<String>("creator")
                            }
                        )
                    result.success(response)
                }
            }

            "uploadObject" -> {
                Log.d("gf_sdk", "upload object call")

                CoroutineScope(Dispatchers.IO).launch {
                    val response =
                        GFObject(call.argument<String>("authKey")!!).uploadFile(
                            object : CreateObjectEstimateOpts {
                                override val ContentLength = 0
                                override val ExpectedChecksums = arrayOf<String>()
                                override val Visibility = ""
                                override val FileType = ""
                                override val ObjectName = call.argument<String>("objectName")
                                override val BucketName = call.argument<String>("bucketName")
                                override val Creator = call.argument<String>("creator")
                            },
                            call.argument<String>("txHash")!!,
                            call.argument<String>("filePath")!!
                        )
                    result.success(response)
                }
            }

            "getUserBuckets" -> {
                Log.d("gf_sdk", "getting user buckets")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Account(call.argument<String>("address")!!).getUserBuckets(
                        call.argument<String>("spUrl")!!
                    )
                    result.success(response)
                }
            }

            "computeHash" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val buffer = call.argument<ByteArray>("buffer")!!
                    val segmentSize = call.argument<Long>("segmentSize")!!.toLong()
                    val dataBlocks = call.argument<Long>("dataBlocks")!!.toLong()
                    val parityBlocks = call.argument<Long>("parityBlocks")!!.toLong()
                    result.success(
                        encoder.computeHash(
                            buffer,
                            segmentSize,
                            dataBlocks,
                            parityBlocks
                        )
                    )
                }
            }

            "encodeRawSegment" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val data = call.argument<ByteArray>("data")!!
                    val dataBlocks = call.argument<Long>("dataBlocks")!!.toLong()
                    val parityBlocks = call.argument<Long>("parityBlocks")!!.toLong()
                    result.success(encoder.encodeRawSegment(data, dataBlocks, parityBlocks))
                }
            }

            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
