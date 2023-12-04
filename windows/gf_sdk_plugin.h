#ifndef FLUTTER_PLUGIN_GF_SDK_PLUGIN_H_
#define FLUTTER_PLUGIN_GF_SDK_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace gf_sdk {

class GfSdkPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  GfSdkPlugin();

  virtual ~GfSdkPlugin();

  // Disallow copy and assign.
  GfSdkPlugin(const GfSdkPlugin&) = delete;
  GfSdkPlugin& operator=(const GfSdkPlugin&) = delete;

  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace gf_sdk

#endif  // FLUTTER_PLUGIN_GF_SDK_PLUGIN_H_
