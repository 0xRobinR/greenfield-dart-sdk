package com.greenfield.sdk.gf_sdk.auth

import android.util.Base64
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Sha256Hash
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex
import java.security.MessageDigest
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
        val signature = generateSignature(canonicalRequest)
        return "GNFD1-ECDSA, Signature=$signature"
    }

    private fun buildCanonicalRequest(
        httpMethod: String,
        canonicalUri: String,
        canonicalQueryString: String,
        canonicalHeaders: String,
        signedHeaders: String
    ): String {
        return "$httpMethod\n$canonicalUri\n$canonicalQueryString\n$canonicalHeaders\n$signedHeaders"
    }

    private fun generateSignature(canonicalRequest: String): String {
        val keccak256Hash = keccak256(canonicalRequest.toByteArray())
        val sha256Hash = Sha256Hash.wrap(keccak256Hash)
        val privateKey = ECKey.fromPrivate(Hex.decode(privateKeyHex))
        val ecdsaSignature = privateKey.sign(sha256Hash)

        val signatureBytes = ecdsaSignature.encodeToDER()
        val signatureBase64 = Base64.encodeToString(signatureBytes, Base64.NO_WRAP)
        return signatureBase64
    }

    private fun keccak256(input: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance("KECCAK-256")
        digest.update(input)
        return digest.digest()
    }
}
