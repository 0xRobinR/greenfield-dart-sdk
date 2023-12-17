package com.greenfield.sdk.gf_sdk

import android.util.Log
import com.greenfield.sdk.gf_sdk.api.Account
import com.greenfield.sdk.gf_sdk.api.Bucket
import com.greenfield.sdk.gf_sdk.api.CreateBucketOpts
import com.greenfield.sdk.gf_sdk.api.Greenfield
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

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "getPlatformVersion" -> {
                Log.d("gf_sdk", "fetching tendermint status")
                CoroutineScope(Dispatchers.IO).launch {
                    GFApiCall().makeNetworkCall().run { Log.d("gf_sdk", this.toString()) }
                    result.success("Android ${android.os.Build.VERSION.RELEASE}")
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

            "createObject" -> {
                Log.d("gf_sdk", "creating object")
                CoroutineScope(Dispatchers.IO).launch {
                    val response = Bucket(call.argument<String>("bucketName")!!).createObject(
                        call.argument<String>("objectName")!!,
                        call.argument<String>("objectData")!!,
                        call.argument<String>("spUrl")!!
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
