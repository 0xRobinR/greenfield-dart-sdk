import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:gf_sdk/interfaces/gf_global.dart';
import 'package:gf_sdk/models/CreateObjectApproval.dart';

import 'gf_sdk_platform_interface.dart';

class MethodChannelGfSdk extends GfSdkPlatform {
  @visibleForTesting
  final methodChannel = const MethodChannel('gf_sdk');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>("getPlatformVersion");
    return version;
  }

  @override
  Future<String?> getApproval({required String authKey, required String primaryAddress, required String bucketName, required String spAddress}) async {
    final approval = await methodChannel.invokeMethod<String>("getApproval", {
      "authKey": authKey,
      "primaryAddress": primaryAddress,
      "bucketName": bucketName,
      "spAddress": spAddress
    });
    return approval;
  }

  @override
  Future<String?> createBucketApproval({required String authKey, required String primaryAddress, required String bucketName, required String spAddress, String network="testnet"}) async {
    final approval = await methodChannel.invokeMethod<String>("createBucketGetApproval", {
      "privateKey": authKey,
      "creator": primaryAddress,
      "bucketName": bucketName,
      "primarySpAddress": spAddress,
      "networkConfig": network,
    });
    return approval;
  }

  @override
  Future<String?> createBucket({required String authKey, required String primaryAddress, required String bucketName, required String spAddress}) async {
    final bucket = await methodChannel.invokeMethod<String>("createBucket", {
      "authKey": authKey,
      "primaryAddress": primaryAddress,
      "bucketName": bucketName,
      "spAddress": spAddress
    });
    return bucket;
  }

  @override
  Future<String?> getStorageProviders() async {
    final sp = await methodChannel.invokeMethod<String>("getStorageProviders");
    return sp;
  }

  @override
  Future<String?> getAccountInfo({required String address}) async {
    final account = await methodChannel.invokeMethod<String>("getAccountInfo", {
      "address": address
    });
    return account;
  }

  @override
  Future<String?> getAccountBalance({required String address}) async {
    final balance = await methodChannel.invokeMethod<String>("getAccountBalance", {
      "address": address
    });
    return balance;
  }

  @override
  Future<String?> getStats() async {
    final stats = await methodChannel.invokeMethod<String>("getStats");
    return stats;
  }

  @override
  Future<String?> getUserBuckets({required String address, required String spUrl}) async {
    final buckets = await methodChannel.invokeMethod<String>("getUserBuckets", {
      "address": address,
      "spUrl": spUrl
    });
    return buckets;
  }

  @override
  Future<String?> getBucketInfo({required String bucketName}) async {
    final bucket = await methodChannel.invokeMethod<String>("getBucketInfo", {
      "bucketName": bucketName
    });
    return bucket;
  }

  @override
  Future<String?> getBucketObjects({required String bucketName}) async {
    final objects = await methodChannel.invokeMethod<String>("getBucketObjects", {
      "bucketName": bucketName
    });
    return objects;
  }

  @override
  Future<String> computeHash(Uint8List buffer, int segmentSize, int dataBlocks, int parityBlocks) async {
    final String result = await methodChannel.invokeMethod('computeHash', {
      'buffer': buffer,
      'segmentSize': segmentSize,
      'dataBlocks': dataBlocks,
      'parityBlocks': parityBlocks,
    });
    return result;
  }

  @override
  Future<String> encodeRawSegment(Uint8List data, int dataBlocks, int parityBlocks) async {
    final String result = await methodChannel.invokeMethod('encodeRawSegment', {
      'data': data,
      'dataBlocks': dataBlocks,
      'parityBlocks': parityBlocks,
    });
    return result;
  }

  @override
  Future<String> createObjectEstimate({
    required String authKey,
    required CreateObjectEstimate opts}) async {
    final String result = await methodChannel.invokeMethod('createObjectEstimate', {
      "authKey": authKey,
      "contentLength": opts.contentLength,
      "expectedChecksums": opts.expectedChecksums,
      "fileType": opts.fileType,
      "objectName": opts.objectName,
      "bucketName": opts.bucketName,
      "creator": opts.creator,
      "visibility": opts.visibility == GfVisibilityType.public ? "VISIBILITY_TYPE_PUBLIC_READ" : "VISIBILITY_TYPE_PRIVATE"
    });
    return result;
  }

  @override
  Future<String> createObject({
    required String authKey,
    required CreateObjectEstimate opts}) async {
    final String result = await methodChannel.invokeMethod('createObject', {
      "authKey": authKey,
      "contentLength": opts.contentLength,
      "expectedChecksums": opts.expectedChecksums,
      "fileType": opts.fileType,
      "objectName": opts.objectName,
      "bucketName": opts.bucketName,
      "creator": opts.creator,
      "visibility": opts.visibility == GfVisibilityType.public ? "VISIBILITY_TYPE_PUBLIC_READ" : "VISIBILITY_TYPE_PRIVATE"
    });
    return result;
  }

  @override
  Future<String> createFolder({
    required String authKey,
    required CreateObjectEstimate opts}) async {
    final String result = await methodChannel.invokeMethod('createFolder', {
      "authKey": authKey,
      "objectName": opts.objectName,
      "bucketName": opts.bucketName,
      "creator": opts.creator
    });
    return result;
  }

  @override
  Future<String> uploadObject({required String authKey, required CreateObjectEstimate opts, required String filePath, required String txHash}) async {
    final String result = await methodChannel.invokeMethod('uploadObject', {
      "authKey": authKey,
      "objectName": opts.objectName,
      "bucketName": opts.bucketName,
      "creator": opts.creator,
      "filePath": filePath,
      "txHash": txHash
    });
    return result;
  }

  @override
  Future<String> deleteObject({required String authKey, required String objectName, required String bucketName, required String creator}) async {
    final String result = await methodChannel.invokeMethod('deleteObject', {
      "authKey": authKey,
      "objectName": objectName,
      "bucketName": bucketName,
      "creator": creator
    });
    return result;
  }

  @override
  Future<String> cancelObject({required String authKey, required String objectName, required String bucketName, required String creator}) async {
    final String result = await methodChannel.invokeMethod('cancelObject', {
      "authKey": authKey,
      "objectName": objectName,
      "bucketName": bucketName,
      "creator": creator
    });
    return result;
  }

  @override
  Future<String> updateObject({required String authKey, required String objectName, required String bucketName, required String creator, required GfVisibilityType visibilityType}) async {
    final String result = await methodChannel.invokeMethod('updateObject', {
      "authKey": authKey,
      "objectName": objectName,
      "bucketName": bucketName,
      "creator": creator,
      "visibility": visibilityType == GfVisibilityType.public ? "VISIBILITY_TYPE_PUBLIC_READ" : "VISIBILITY_TYPE_PRIVATE"
    });
    return result;
  }
}
