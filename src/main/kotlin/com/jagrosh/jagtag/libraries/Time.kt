package com.jagrosh.jagtag.libraries

import com.jagrosh.jagtag.Environment
import com.jagrosh.jagtag.Method
import java.lang.Long.parseLong
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Time {
    fun getMethods(): Collection<Method> {
        return listOf(

            // gets the current time
            Method("now", {
                OffsetDateTime.now(ZoneId.of("Z")).format(DateTimeFormatter.RFC_1123_DATE_TIME)
            }, { _: Environment, input: Array<String> ->
                try {
                    return@Method OffsetDateTime.now().format(DateTimeFormatter.ofPattern(input[0]))
                } catch (e: Exception) {
                    return@Method "<invalid time format>"
                }
            }, null),

            Method("time", complex = { _: Environment, input: Array<String> ->
                val parts: Array<String> = input[0].split("\\|", limit = 2).toTypedArray()
                val epoch: Long
                try {
                    epoch = parseLong(parts[0])
                } catch (e: Exception) {
                    return@Method "<invalid epoch millis>"
                }
                val format: DateTimeFormatter?
                format = if (parts.size == 1) {
                    DateTimeFormatter.RFC_1123_DATE_TIME
                } else {
                    try {
                        DateTimeFormatter.ofPattern(parts[1])
                    } catch (e: Exception) {
                        return@Method "<invalid time format>"
                    }
                }
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.of("Z")).format(format)
            })
        )
    }
}