package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        fun changeEmptyToNull(candidate: String?) =
            if (candidate.isNullOrBlank()) null else candidate

        val parts: List<String>? = fullName?.split(" ")

        val firstName = changeEmptyToNull(parts?.getOrNull(0))
        val lastName = changeEmptyToNull(parts?.getOrNull(1))
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val translitDict = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
        )
        val translit = StringBuilder()
        val nameParts = payload.toLowerCase(Locale.ROOT).split(" ")
        for ((partIndex, namePart) in nameParts.withIndex()) {
            for ((index, letter) in namePart.withIndex()) {
                val candidate = StringBuilder(translitDict[letter] ?: letter.toString())
                if (index == 0) {
                    candidate[0] = candidate[0].toUpperCase()
                }
                translit.append(candidate)
            }
            if (partIndex < nameParts.size - 1) {
                translit.append(divider)
            }
        }
        return translit.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? = when {
        firstName.isNullOrBlank() && lastName.isNullOrBlank() -> null
        !firstName.isNullOrBlank() && lastName.isNullOrBlank() -> firstName[0].toString()
            .toUpperCase(Locale.ROOT)
        firstName.isNullOrBlank() && !lastName.isNullOrBlank() -> lastName[0].toString()
            .toUpperCase(Locale.ROOT)
        else -> "${firstName?.get(0)}${lastName?.get(0)}".toUpperCase(Locale.ROOT)
    }

    fun String.truncate(allowedCharsCount: Int): String {
        if (trim().length < allowedCharsCount) {
            return trim()
        }
        return "${trim().substring(0, allowedCharsCount - 1)}..."
    }

    fun String.stripHtml() = replace(Regex("<[^>]*>"), "")
        .replace(Regex("\\s{2,}"), " ")

}
