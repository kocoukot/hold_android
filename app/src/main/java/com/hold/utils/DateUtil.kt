package com.hold.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val dateFormatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    private val recordTimeFormat = SimpleDateFormat("mm:ss.SS", Locale.US)
    private val timerFormat = SimpleDateFormat("mm:ss:SS", Locale.US)


    fun getRecordDate(recordDate: Long): String = dateFormatter.format(Date(recordDate))

    fun getRecordResult(recordResult: Long): String {
        val hours = recordResult / 3600000
        val minutes = (recordResult % 3600000) / 60000
        val seconds = (recordResult % 60000) / 1000
        val millis = (recordResult - (hours * 3600 + minutes * 60 + seconds) * 1000) / 10
        return String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, millis)
    }


    fun timeTimerFormat(time: Long): CharSequence = timerFormat.format(time)

}