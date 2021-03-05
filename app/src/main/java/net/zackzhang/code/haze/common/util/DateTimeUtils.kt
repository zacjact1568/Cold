package net.zackzhang.code.haze.common.util

import java.time.*
import java.time.format.DateTimeParseException
import java.time.zone.ZoneRulesException

object DateTimeUtils {

    fun parseDate(date: String) = try {
        LocalDate.parse(date)
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        null
    }

    fun parseTime(time: String) = try {
        LocalTime.parse(time)
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        null
    }

    fun parseDateTime(dateTime: String) = try {
        ZonedDateTime.parse(dateTime)
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        null
    }

    fun parseTimeZone(timeZone: String) = try {
        ZoneId.of(timeZone)
    } catch (e: DateTimeException) {
        e.printStackTrace()
        null
    } catch (e: ZoneRulesException) {
        e.printStackTrace()
        null
    }

    fun parseUtcOffset(utcOffset: String) = try {
        ZoneOffset.of(utcOffset)
    } catch (e: DateTimeException) {
        e.printStackTrace()
        null
    }
}