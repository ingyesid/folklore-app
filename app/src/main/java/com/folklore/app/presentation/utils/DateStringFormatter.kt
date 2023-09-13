package com.folklore.app.presentation.utils

import android.content.Context
import android.text.format.DateUtils
import com.folklore.domain.utils.ReadableTimeFormatter
import java.util.Date
import javax.inject.Inject

class DateStringFormatter @Inject constructor(
    private val context: Context,
) : ReadableTimeFormatter {
    override fun getReadableTime(date: Date): String {
        return DateUtils.getRelativeDateTimeString(
            context,
            date.time,
            DateUtils.DAY_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            DateUtils.FORMAT_SHOW_DATE,
        ).toString()
    }
}
