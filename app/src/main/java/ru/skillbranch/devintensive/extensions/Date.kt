package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

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
    }
    this.time = time
    return this
}

enum class TimeUnits(private val normalForm: String) {
    SECOND("секунд"),
    MINUTE("минут"),
    HOUR("час"),
    DAY("д");

    fun plural(number: Int): String {
        val dozens = (number % 100) / 10
        val units = number % 10
        if (dozens == 1) {
            return when (this) {
                SECOND, MINUTE -> normalForm
                HOUR -> "$number ${normalForm}ов"
                DAY -> "$number ${normalForm}ней"
            }
        }
        return when (this) {
            SECOND, MINUTE ->
                when (units) {
                    1 -> "$number ${this.normalForm}у"
                    2, 3, 4 -> "$number ${this.normalForm}ы"
                    else -> "$number ${this.normalForm}"
                }
            HOUR ->
                when (units) {
                    1 -> "$number ${this.normalForm}"
                    2, 3, 4 -> "$number ${this.normalForm}а"
                    else -> "$number ${this.normalForm}ов"
                }
            DAY ->
                when (units) {
                    1 -> "$number ${this.normalForm}ень"
                    2, 3, 4 -> "$number ${this.normalForm}ня"
                    else -> "$number ${this.normalForm}ней"
                }
        }
    }

}

fun Date.humanizeDiff(date: Date = Date()): String {
    val absDiff = abs(date.time - time)
    val isPast = date.time - time > 0

    fun getPastOrFutureVariant(past: String, future: String) = if (isPast) past else future

    fun getStandardPastOrFutureVariant(declinedVariant: String) =
        if (isPast) "$declinedVariant назад" else "через $declinedVariant"

    return when {
        absDiff / DAY > 360 -> getPastOrFutureVariant("более года назад", "более чем через год")
        absDiff >= 26 * HOUR -> {
            val daysPassed = (absDiff / DAY).toInt()
            getStandardPastOrFutureVariant(TimeUnits.DAY.plural(daysPassed))
        }
        absDiff >= 22 * HOUR -> getStandardPastOrFutureVariant("день")
        absDiff >= 75 * MINUTE -> {
            val hoursPassed = (absDiff / HOUR).toInt()
            getStandardPastOrFutureVariant(TimeUnits.HOUR.plural(hoursPassed))
        }
        absDiff >= 45 * MINUTE -> getStandardPastOrFutureVariant("час")
        absDiff >= 75 * SECOND -> {
            val minutesPassed = (absDiff / MINUTE).toInt()
            getStandardPastOrFutureVariant(TimeUnits.MINUTE.plural(minutesPassed))
        }
        absDiff >= 45 * SECOND -> {
            getStandardPastOrFutureVariant("минуту")
        }
        absDiff >= SECOND -> {
            getStandardPastOrFutureVariant("несколько секунд")
        }
        else -> "только что"
    }
}