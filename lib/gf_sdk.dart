
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
    return GfSdkPlatform.instance.getUserBuckets(address: address, spAddress: spAddress);
  }

  Future<String?> getBucketInfo({required String bucketName}) {
    return GfSdkPlatform.instance.getBucketInfo(bucketName: bucketName);
  }

  Future<String?> getBucketObjects({required String bucketName}) {
    return GfSdkPlatform.instance.getBucketObjects(bucketName: bucketName);
  }
}
