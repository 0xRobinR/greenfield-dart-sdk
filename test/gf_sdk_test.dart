import 'dart:typed_data';

import 'package:flutter_test/flutter_test.dart';
import 'package:gf_sdk/gf_sdk.dart';
import 'package:gf_sdk/gf_sdk_platform_interface.dart';
import 'package:gf_sdk/gf_sdk_method_channel.dart';
import 'package:gf_sdk/models/CreateObjectApproval.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockGfSdkPlatform
    with MockPlatformInterfaceMixin
    implements GfSdkPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<String> computeHash(Uint8List buffer, int segmentSize, int dataBlocks, int parityBlocks) {
    // TODO: implement computeHash
    throw UnimplementedError();
  }

  @override
  Future<String?> createBucket({required String authKey, required String primaryAddress, required String bucketName, required String spAddress}) {
    // TODO: implement createBucket
    throw UnimplementedError();
  }

  @override
  Future<String> createFolder({required String authKey, required CreateObjectEstimate opts}) {
    // TODO: implement createFolder
    throw UnimplementedError();
  }

  @override
  Future<String> createObject({required String authKey, required CreateObjectEstimate opts}) {
    // TODO: implement createObject
    throw UnimplementedError();
  }

  @override
  Future<String> createObjectEstimate({required String authKey, required CreateObjectEstimate opts}) {
    // TODO: implement createObjectEstimate
    throw UnimplementedError();
  }

  @override
  Future<String> encodeRawSegment(Uint8List data, int dataBlocks, int parityBlocks) {
    // TODO: implement encodeRawSegment
    throw UnimplementedError();
  }

  @override
  Future<String?> getAccountBalance({required String address}) {
    // TODO: implement getAccountBalance
    throw UnimplementedError();
  }

  @override
  Future<String?> getAccountInfo({required String address}) {
    // TODO: implement getAccountInfo
    throw UnimplementedError();
  }

  @override
  Future<String?> getApproval({required String authKey, required String primaryAddress, required String bucketName, required String spAddress}) {
    // TODO: implement getApproval
    throw UnimplementedError();
  }

  @override
  Future<String?> getBucketInfo({required String bucketName}) {
    // TODO: implement getBucketInfo
    throw UnimplementedError();
  }

  @override
  Future<String?> getBucketObjects({required String bucketName}) {
    // TODO: implement getBucketObjects
    throw UnimplementedError();
  }

  @override
  Future<String?> getStats() {
    // TODO: implement getStats
    throw UnimplementedError();
  }

  @override
  Future<String?> getStorageProviders() {
    // TODO: implement getStorageProviders
    throw UnimplementedError();
  }

  @override
  Future<String?> getUserBuckets({required String address, required String spUrl}) {
    // TODO: implement getUserBuckets
    throw UnimplementedError();
  }

  @override
  Future<String> uploadObject({required String authKey, required CreateObjectEstimate opts, required String filePath, required String txHash}) {
    // TODO: implement uploadObject
    throw UnimplementedError();
  }
}

void main() {
  final GfSdkPlatform initialPlatform = GfSdkPlatform.instance;

  test('$MethodChannelGfSdk is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelGfSdk>());
  });

  test('getPlatformVersion', () async {
    GfSdk gfSdkPlugin = GfSdk();
    MockGfSdkPlatform fakePlatform = MockGfSdkPlatform();
    GfSdkPlatform.instance = fakePlatform;

    expect(await gfSdkPlugin.getPlatformVersion(), '42');
  });
}
