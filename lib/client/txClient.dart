import 'dart:typed_data';

import 'package:gf_sdk/types/tx.dart';

class Tx {
  Any generateMsg(String typeUrl, Uint8List msgBytes) {
    return Any.fromPartial(typeUrl: typeUrl, value: msgBytes);
  }

  Uint8List getBodyBytes(List<Map<String, dynamic>> params) {
    List<Any> multiMsgBytes = params.map((tx) {
      return generateMsg(tx['typeUrl'], tx['msgBytes']);
    }).toList();

    TxBody txBody = TxBody.fromPartial(messages: multiMsgBytes);
    return txBody.encode();
  }
}
