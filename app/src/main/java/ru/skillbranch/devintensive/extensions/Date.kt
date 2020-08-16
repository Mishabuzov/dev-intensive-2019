package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

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

    fun plural(unit: Int): String = when (this) {
        SECOND, MINUTE ->
            when (unit) {
                1 -> "$unit ${this.normalForm}у"
                2, 3, 4 -> "$unit ${this.normalForm}ы"
                else -> "$unit ${this.normalForm}"
            }
        HOUR ->
            when (unit) {
                1 -> "$unit ${this.normalForm}"
                2, 3, 4 -> "$unit ${this.normalForm}а"
                else -> "$unit ${this.normalForm}ов"
            }
        DAY ->
            when (unit) {
                1 -> "$unit ${this.normalForm}ень"
                2, 3, 4 -> "$unit ${this.normalForm}ня"
                else -> "$unit ${this.normalForm}ней"
            }
    }

}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - time  // + second for considering processing time.

    return when {
        diff / DAY > 365 -> "более года назад"
        diff >= DAY -> {
            val daysPassed = (diff / DAY).toInt()
            "${TimeUnits.DAY.plural(daysPassed)} назад"
        }
        diff >= HOUR -> {
            val hoursPassed = (diff / HOUR).toInt()
            "${TimeUnits.HOUR.plural(hoursPassed)} назад"
        }
        diff >= MINUTE -> {
            val minutesPassed = (diff / MINUTE).toInt()
            "${TimeUnits.MINUTE.plural(minutesPassed)} назад"
        }
        diff >= SECOND -> {
            val secondsPassed = (diff / MINUTE).toInt()
            "${TimeUnits.SECOND.plural(secondsPassed)} назад"
        }
        else -> "только что"
    }
}