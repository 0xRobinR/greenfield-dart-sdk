import 'dart:typed_data';

class Any {
  String typeUrl;
  Uint8List value;

  Any.fromPartial({required this.typeUrl, required this.value});
}

class TxBody {
  List<Any> messages;
  String? memo;
  int? timeoutHeight;
  List<Any>? extensionOptions;
  List<Any>? nonCriticalExtensionOptions;

  TxBody.fromPartial({required this.messages, this.memo, this.timeoutHeight, this.extensionOptions, this.nonCriticalExtensionOptions});

  Uint8List encode() {
    return Uint8List(0);
  }
}