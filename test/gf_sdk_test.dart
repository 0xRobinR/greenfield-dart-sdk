import 'package:flutter_test/flutter_test.dart';
import 'package:gf_sdk/gf_sdk.dart';
import 'package:gf_sdk/gf_sdk_platform_interface.dart';
import 'package:gf_sdk/gf_sdk_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockGfSdkPlatform
    with MockPlatformInterfaceMixin
    implements GfSdkPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
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
