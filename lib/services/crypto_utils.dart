import 'dart:convert';
import 'package:crypto/crypto.dart';
import 'package:convert/convert.dart';

class CryptoUtils {
  static String generateAuthorizationHeader(String privateKey, String method, String canonicalUri, String canonicalQueryString, String canonicalHeaders, String signedHeaders, String payload) {
    final expiryTimestamp = _generateExpiryTimestamp();
    final canonicalRequest = _createCanonicalRequest(method, canonicalUri, canonicalQueryString, canonicalHeaders, signedHeaders, payload);
    final hashedCanonicalRequest = _hashCanonicalRequest(canonicalRequest);
    final signature = _signRequest(privateKey, hashedCanonicalRequest);
    return 'GNFD1-ECDSA $expiryTimestamp $hashedCanonicalRequest $signature';
  }

  static String _generateExpiryTimestamp() {
    return (DateTime.now().millisecondsSinceEpoch / 1000).floor().toString();
  }

  static String _createCanonicalRequest(String method, String canonicalUri, String canonicalQueryString, String canonicalHeaders, String signedHeaders, String payload) {
    return '$method\n$canonicalUri\n$canonicalQueryString\n$canonicalHeaders\n$signedHeaders\n$payload';
  }

  static String _hashCanonicalRequest(String canonicalRequest) {
    final bytes = utf8.encode(canonicalRequest);
    final digest = sha256.convert(bytes);
    return hex.encode(digest.bytes);
  }

  static String _signRequest(String privateKey, String hashedCanonicalRequest) {
    final bytes = utf8.encode(hashedCanonicalRequest);
    final digest = sha256.convert(bytes);
    final signature = hex.encode(digest.bytes);
    return _sign(privateKey, signature);
  }

  static String _sign(String privateKey, String hashedCanonicalRequest) {
    final key = utf8.encode(privateKey);
    final bytes = utf8.encode(hashedCanonicalRequest);

    final hmacSha256 = Hmac(sha256, key);
    final digest = hmacSha256.convert(bytes);

    return base64.encode(digest.bytes);
  }
}
