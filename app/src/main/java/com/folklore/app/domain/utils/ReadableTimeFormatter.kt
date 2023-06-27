package com.folklore.app.domain.utils

import java.util.Date

@FunctionalInterface
interface ReadableTimeFormatter {
    fun getReadableTime(date: Date): String
}
