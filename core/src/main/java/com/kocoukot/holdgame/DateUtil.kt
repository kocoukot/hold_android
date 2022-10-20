package com.kocoukot.holdgame

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {
    private val dateFormatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    private val recordTimeFormat = SimpleDateFormat("mm:ss.SS", Locale.US)
    private val timerFormat = SimpleDateFormat("mm:ss:SS", Locale.US)
    var inputFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault())


    fun getRecordDate(recordDate: Long): String = dateFormatter.format(Date(recordDate))

    fun getRecordResult(recordResult: Long): String {
        val hours = recordResult / 3600000
        val minutes = (recordResult % 3600000) / 60000
        val seconds = (recordResult % 60000) / 1000
        val millis = (recordResult - (hours * 3600 + minutes * 60 + seconds) * 1000) / 10
        return String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, millis)
    }

    fun toRecordResolve(result: Long): String {
        println("game result $result")
        return when {
            result < 60000 -> "${result.toDouble() / 1000} sec"
            result >= 60000 -> {
                val min = result / 60000
                val sec = result - (min * 60000)
                "$min min ${sec.toDouble() / 1000} sec"
            }
            else -> ""
        }
    }

    fun timeTimerFormat(time: Long): CharSequence = timerFormat.format(time)

    fun convertGlobalTime(time: String) =
        LocalDateTime.parse(time, inputFormatter).toEpochSecond(ZoneOffset.UTC) * 1000
}