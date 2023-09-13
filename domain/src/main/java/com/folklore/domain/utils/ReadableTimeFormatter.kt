package com.folklore.domain.utils

import java.util.Date


interface ReadableTimeFormatter {
    fun getReadableTime(date: Date): String
}
