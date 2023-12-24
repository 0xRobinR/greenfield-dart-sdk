
import 'dart:typed_data';

import 'package:gf_sdk/constants.dart';
import 'package:gf_sdk/models/CreateObjectApproval.dart';

import 'gf_sdk_platform_interface.dart';

class GfSdk {
  Future<String?> getPlatformVersion() {
    return GfSdkPlatform.instance.getPlatformVersion();
  }

  Future<String?> getApproval(
      {required String authKey,
      required String primaryAddress,
      required String spAddress,
      required String bucketName}) {
    return GfSdkPlatform.instance.getApproval(authKey: authKey, primaryAddress: primaryAddress, bucketName: bucketName, spAddress: spAddress);
  }

  Future<String?> createBucket(
      {required String authKey,
      required String primaryAddress,
      required String spAddress,
      required String bucketName}) {
    return GfSdkPlatform.instance.createBucket(authKey: authKey, primaryAddress: primaryAddress, bucketName: bucketName, spAddress: spAddress);
  }

  Future<String?> getStorageProviders() {
    return GfSdkPlatform.instance.getStorageProviders();
  }

  Future<String?> getAccountInfo({required String address}) {
    return GfSdkPlatform.instance.getAccountInfo(address: address);
  }

  Future<String?> getAccountBalance({required String address}) {
    return GfSdkPlatform.instance.getAccountBalance(address: address);
  }

  Future<String?> getStats() {
    return GfSdkPlatform.instance.getStats();
  }

  Future<String?> getUserBuckets({required String address, required String spAddress}) {
    return GfSdkPlatform.instance.getUserBuckets(address: address, spUrl: spAddress);
  }

  Future<String?> getBucketInfo({required String bucketName}) {
    return GfSdkPlatform.instance.getBucketInfo(bucketName: bucketName);
  }

  Future<String?> getBucketObjects({required String bucketName}) {
    return GfSdkPlatform.instance.getBucketObjects(bucketName: bucketName);
  }

  Future<String> computeHash(
      {required Uint8List buffer,
      int segmentSize = DEFAULT_SEGMENT_SIZE,
      int dataBlocks = DEFAULT_DATA_BLOCKS,
      int parityBlocks = DEFAULT_PARITY_BLOCKS}) async {
    return GfSdkPlatform.instance.computeHash(buffer, segmentSize, dataBlocks, parityBlocks);
  }

  Future<String> encodeRawSegment({required Uint8List data,
    int dataBlocks = DEFAULT_DATA_BLOCKS,
    int parityBlocks = DEFAULT_PARITY_BLOCKS}) async {
    return GfSdkPlatform.instance.encodeRawSegment(data, dataBlocks, parityBlocks);
  }

  Future<String> createObjectEstimate({required String authKey, required CreateObjectEstimate opts}) async {
    return GfSdkPlatform.instance.createObjectEstimate(authKey: authKey, opts: opts);
  }

  Future<String> createObject({required String authKey, required CreateObjectEstimate opts}) async {
    return GfSdkPlatform.instance.createObject(authKey: authKey, opts: opts);
  }

  Future<String> createFolder({required String authKey, required CreateObjectEstimate opts}) async {
    return GfSdkPlatform.instance.createFolder(authKey: authKey, opts: opts);
  }


}

