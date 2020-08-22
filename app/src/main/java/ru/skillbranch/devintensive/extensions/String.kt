package ru.skillbranch.devintensive.ru.skillbranch.devintensive.extensions

fun String.truncate(allowedCharsCount: Int = 16): String {
    if (trim().length <= allowedCharsCount) {
        return trim()
    }
    return "${trim().substring(0, allowedCharsCount).trim()}..."
}

fun String.stripHtml(): String {
    val htmlRegex = Regex("(<.*?>)|(&[^ а-я]{1,4}?;)")
    val spaceRegex = Regex(" {2,}")
    return replace(htmlRegex, "").replace(spaceRegex, " ")
}