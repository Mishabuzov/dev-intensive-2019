package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        fun changeEmptyToNull(candidate: String?) =
            if (candidate.isNullOrBlank()) null else candidate

        val parts: List<String>? = fullName?.trim()?.split(" ")

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
        for (letter in payload.trim()) {
            val candidate = StringBuilder(translitDict[letter.toLowerCase()] ?: letter.toString())
            if (letter.isUpperCase() && candidate.isNotEmpty()) {
                candidate[0] = candidate[0].toUpperCase()
            }
            translit.append(candidate)
        }
        return translit.toString().replace(" ", divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String? = when {
        firstName.isNullOrBlank() && lastName.isNullOrBlank() -> null
        !firstName.isNullOrBlank() && lastName.isNullOrBlank() -> firstName.trim()[0].toString()
            .toUpperCase(Locale.ROOT)
        firstName.isNullOrBlank() && !lastName.isNullOrBlank() -> lastName.trim()[0].toString()
            .toUpperCase(Locale.ROOT)
        else -> "${firstName?.trim()?.get(0)}${lastName?.trim()?.get(0)}".toUpperCase(Locale.ROOT)
    }

}
