package com.nightfury.moviedb.presentation.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateUtil {
    companion object {
        fun getPrevMonthDate(): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)
            val df = SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH)
            return df.format(calendar.time)
        }
    }
}