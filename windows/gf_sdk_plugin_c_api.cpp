#include "include/gf_sdk/gf_sdk_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "gf_sdk_plugin.h"

void GfSdkPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  gf_sdk::GfSdkPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
