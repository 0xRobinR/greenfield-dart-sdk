
import 'gf_sdk_platform_interface.dart';

class GfSdk {
  Future<String?> getPlatformVersion() {
    return GfSdkPlatform.instance.getPlatformVersion();
  }
}
