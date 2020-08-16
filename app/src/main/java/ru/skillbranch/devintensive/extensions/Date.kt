package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val WEEK = 7 * DAY
const val MONTH = 4 * WEEK
const val YEAR = 12 * MONTH


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        TimeUnits.WEEK -> value * WEEK
        TimeUnits.MONTH -> value * MONTH
        TimeUnits.YEAR -> value * YEAR
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY,
    WEEK,
    MONTH,
    YEAR
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - time  // + second for considering processing time.

    return when {
        diff / MONTH == 12L -> "год назад"
        diff >= YEAR -> "более года назад"
        diff >= MONTH -> {
            val monthsPassed = diff / MONTH
            val declension = when (monthsPassed) {
                1L -> ""
                2L, 3L, 4L -> "а"
                else -> "ев"
            }
            "$monthsPassed месяц$declension назад"
        }
        diff >= WEEK -> {
            val weeksPassed = diff / WEEK
            val declension = when (weeksPassed) {
                1L -> "ю"
                2L, 3L, 4L -> "и"
                else -> "ь"
            }
            "$weeksPassed недел$declension назад"
        }
        diff >= DAY -> {
            val daysPassed = diff / DAY
            val declension = when (daysPassed) {
                1L -> "день"
                2L, 3L, 4L -> "дня"
                else -> "дней"
            }
            "$daysPassed $declension назад"
        }
        diff >= HOUR -> {
            val hoursPassed = diff / HOUR
            val declension = when (hoursPassed) {
                1L -> ""
                2L, 3L, 4L -> "а"
                else -> "ов"
            }
            "$hoursPassed час$declension назад"
        }
        diff >= MINUTE -> {
            val minutesPassed = diff / MINUTE
            val declension = when (minutesPassed) {
                1L -> "у"
                2L, 3L, 4L -> "ы"
                else -> ""
            }
            "$minutesPassed минут$declension назад"
        }
        else -> "только что"
    }
}