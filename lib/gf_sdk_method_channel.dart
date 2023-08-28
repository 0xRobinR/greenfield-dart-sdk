import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'gf_sdk_platform_interface.dart';

class MethodChannelGfSdk extends GfSdkPlatform {
  @visibleForTesting
  final methodChannel = const MethodChannel('gf_sdk');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>("getPlatformVersion");
    return version;
  }
}
