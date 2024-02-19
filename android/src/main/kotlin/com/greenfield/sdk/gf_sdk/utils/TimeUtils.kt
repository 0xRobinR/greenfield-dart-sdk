package com.greenfield.sdk.gf_sdk.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

object TimeUtils {
    fun generateExpiryTimestamp(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 604800) // Add 604800 seconds (7 days) to the current time

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return dateFormat.format(calendar.time)
    }
}