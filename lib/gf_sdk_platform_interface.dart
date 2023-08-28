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
}
