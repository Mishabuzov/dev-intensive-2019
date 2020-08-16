package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
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
        for (letter in payload) {
            if (letter in translitDict) {
                translit.append(translitDict[letter])
            } else {
                translit.append(letter)
            }
        }
        return translit.toString().replace(" ", divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String? = when {
        firstName.isNullOrBlank() && lastName.isNullOrBlank() -> null
        !firstName.isNullOrBlank() && lastName.isNullOrBlank() -> firstName[0].toString()
        firstName.isNullOrBlank() && !lastName.isNullOrBlank() -> lastName[0].toString()
        else -> "${firstName?.get(0)}${lastName?.get(0)}"
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
