package com.folklore.app.domain.utils

import java.util.Date

interface ReadableTimeFormatter {
    fun getReadableTime(date: Date): String
}
