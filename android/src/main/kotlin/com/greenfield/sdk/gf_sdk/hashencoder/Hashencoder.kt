package com.greenfield.sdk.gf_sdk.hashencoder

import gf_go_mobile.HashEncoder


class Hashencoder {
    private val hashEncoder = HashEncoder()
    fun computeHash(buffer: ByteArray, segmentSize: Long, dataBlocks: Long, parityBlocks: Long): String {
        // Call the function from your .aar and handle the result
        return hashEncoder.computeHash(buffer, segmentSize, dataBlocks, parityBlocks)
    }

    fun encodeRawSegment(data: ByteArray, dataBlocks: Long, parityBlocks: Long): String {
        // Call the function from your .aar and handle the result
        return hashEncoder.encodeRawSegment(data, dataBlocks, parityBlocks)
    }
}