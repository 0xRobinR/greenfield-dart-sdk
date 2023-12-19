import 'dart:typed_data';

import 'package:gf_sdk/models/CreateObjectApproval.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'gf_sdk_method_channel.dart';

abstract class GfSdkPlatform extends PlatformInterface {
  /// Constructs a GfSdkPlatform.
  GfSdkPlatform() : super(token: _token);

  static final Object _token = Object();

  static GfSdkPlatform _instance = MethodChannelGfSdk();

  /// The default instance of [GfSdkPlatform] to use.
  ///
  /// Defaults to [MethodChannelGfSdk].
  static GfSdkPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [GfSdkPlatform] when
  /// they register themselves.
  static set instance(GfSdkPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> getApproval({required String authKey, required String primaryAddress, required String bucketName, required String spAddress}) {
    throw UnimplementedError('getApproval() has not been implemented.');
  }

  Future<String?> createBucket({required String authKey, required String primaryAddress, required String bucketName, required String spAddress}) {
    throw UnimplementedError('createBucket() has not been implemented.');
  }

  Future<String?> getStorageProviders() {
    throw UnimplementedError('getStorageProviders() has not been implemented.');
  }

  Future<String?> getAccountInfo({required String address}) {
    throw UnimplementedError('getAccountInfo() has not been implemented.');
  }

  Future<String?> getAccountBalance({required String address}) {
    throw UnimplementedError('getAccountBalance() has not been implemented.');
  }

  Future<String?> getStats() {
    throw UnimplementedError('getStats() has not been implemented.');
  }

  Future<String?> getUserBuckets({required String address, required String spUrl}) {
    throw UnimplementedError('getUserBuckets() has not been implemented.');
  }

  Future<String?> getBucketInfo({required String bucketName}) {
    throw UnimplementedError('getBucketInfo() has not been implemented.');
  }

  Future<String?> getBucketObjects({required String bucketName}) {
    throw UnimplementedError('getBucketObjects() has not been implemented.');
  }

  Future<String> computeHash(Uint8List buffer, int segmentSize, int dataBlocks, int parityBlocks) async {
    throw UnimplementedError('computeHash() has not been implemented.');
  }

  Future<String> encodeRawSegment(Uint8List data, int dataBlocks, int parityBlocks) async {
    throw UnimplementedError('encodeRawSegment() has not been implemented.');
  }

  Future<String> createObjectEstimate({required String authKey, required CreateObjectEstimate opts}) async {
    throw UnimplementedError('encodeRawSegment() has not been implemented.');
  }
}
