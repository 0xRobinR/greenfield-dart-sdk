package com.greenfield.sdk.gf_sdk.auth

import android.content.ContentValues.TAG
import android.util.Base64
import android.util.Log
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Sha256Hash
import org.bouncycastle.jcajce.provider.digest.Keccak
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Sign
import org.web3j.utils.Numeric
import java.security.Security

class GreenfieldAuth(private val privateKeyHex: String) {
    init {
        Security.addProvider(BouncyCastleProvider())
    }

    fun generateAuthorizationHeader(
        httpMethod: String,
        canonicalUri: String,
        canonicalQueryString: String,
        canonicalHeaders: String,
        signedHeaders: String
    ): String {

        val canonicalRequest = buildCanonicalRequest(
            httpMethod,
            canonicalUri,
            canonicalQueryString,
            canonicalHeaders,
            signedHeaders
        )

        Log.d(TAG, "getApproval: canonicalHeader - $canonicalRequest")

        val msgToSign = getMsgToSign(canonicalRequest)

        Log.d(TAG, "getApproval: msgToSign - ${msgToSign.toString(Charsets.UTF_8)}")

        val signature = secpSign(msgToSign, privateKeyHex)

        Log.d(TAG, "getApproval: signature - $signature")
//        val signature = generateSignature(canonicalRequest)
        return "GNFD1-ECDSA, Signature=$signature"
    }

    fun buildCanonicalRequest(
        httpMethod: String,
        canonicalUri: String,
        canonicalQueryString: String,
        canonicalHeaders: String,
        signedHeaders: String
    ): String {
        return "$httpMethod\n$canonicalUri\n$canonicalQueryString\n$canonicalHeaders\n$signedHeaders"
    }

    fun secpSign(digestBz: ByteArray, privateKeyHex: String): String {

        try {

            // Decode the private key from hex string
            val privateKey = Numeric.toBigInt(privateKeyHex)
            val keyPair = ECKeyPair.create(privateKey)

            // Sign the digest
            val signatureData = Sign.signMessage(digestBz, keyPair, false)

//        return "signatureData"

            val r = Numeric.toHexStringNoPrefix(signatureData.r)
            val s = Numeric.toHexStringNoPrefix(signatureData.s)
            var v = Integer.parseInt(Numeric.toHexStringNoPrefix(signatureData.v), 16)
//            var v = v_v.toInt()
//
//            // Ethereum uses 27 or 28 as the v value
            if (v < 27) v += 27

            // Concatenate r, s, and v components
            val res = r + s + Numeric.toHexStringNoPrefix(byteArrayOf(v.toByte()))

            Log.d(TAG, "secpSign: res: $res")

            // Process v as per the JavaScript logic
            return when (res.takeLast(2)) {
                "1c" -> res.dropLast(2) + "01"
                "1b" -> res.dropLast(2) + "00"
                else -> res
            }
        } catch (e: Exception) {
            Log.d(TAG, "secpSign: error: $e")
            return ""
        }
    }

    fun getMsgToSign(canonicalRequest: String): ByteArray {
        // Convert the canonicalRequest string to a ByteArray
        val unsignedBytes = canonicalRequest.toByteArray(Charsets.UTF_8)

        // Compute the KECCAK-256 hash
        return keccak256(unsignedBytes)
    }

    private fun keccak256(input: ByteArray): ByteArray {
        val digest = Keccak.Digest256()
        digest.update(input)
        return digest.digest()
    }

//    private fun generateSignature(canonicalRequest: String): String {
//        val keccak256Hash = keccak256(canonicalRequest.toByteArray())
//        val sha256Hash = Sha256Hash.wrap(keccak256Hash)
//        val privateKey = ECKey.fromPrivate(Hex.decode(privateKeyHex))
//        val ecdsaSignature = privateKey.sign(sha256Hash)
//
//        val signatureBytes = ecdsaSignature.encodeToDER()
//        val signatureBase64 = Base64.encodeToString(signatureBytes, Base64.NO_WRAP)
//        return signatureBase64
//    }
//
//    private fun keccak256(input: ByteArray): ByteArray {
//        val digest = MessageDigest.getInstance("KECCAK-256", BouncyCastleProvider.PROVIDER_NAME)
//        digest.update(input)
//        return digest.digest()
//    }

    private fun generateSignature(canonicalRequest: String): String {
        val sha3Hash = sha3_256(canonicalRequest.toByteArray())
        Log.d(
            TAG,
            "generateSignature: sha3Hash: ${Hex.toHexString(sha3Hash)} private key: $privateKeyHex"
        )
        val privateKey = ECKey.fromPrivate(Hex.decode(privateKeyHex))
        Log.d(TAG, "generateSignature: privateKey: $privateKey")
        val signature = privateKey.sign(Sha256Hash.wrap(sha3Hash))
        val signatureBytes = signature.encodeToDER()
        val signatureBase64 = Base64.encodeToString(signatureBytes, Base64.NO_WRAP)
        Log.d(TAG, "generateSignature: signatureBase64: $signatureBase64")
        return signatureBase64
    }

    private fun sha3_256(input: ByteArray): ByteArray {
        val digest = Keccak.Digest256()
        digest.update(input)
        return digest.digest()
    }
}
