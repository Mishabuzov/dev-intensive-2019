package ru.skillbranch.devintensive.ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly
import ru.skillbranch.devintensive.ru.skillbranch.devintensive.models.Bender.Status.CRITICAL
import ru.skillbranch.devintensive.ru.skillbranch.devintensive.models.Bender.Status.NORMAL

class Bender(var status: Status = NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> =
        if (question.answers.contains(answer) || question == Question.IDLE) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            val phrase = StringBuilder("Это неправильный ответ")
            if (status == CRITICAL) {
                phrase.append(". Давай все по новой")
                question = Question.NAME
            }
            status = status.nextStatus()
            "$phrase\n${question.question}" to status.color
        }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun isValidAnswer(answer: String): Boolean = answer[0].isUpperCase()
            override fun getErrorMessage(): String = "Имя должно начинаться с заглавной буквы"
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun isValidAnswer(answer: String): Boolean = answer[0].isLowerCase()
            override fun getErrorMessage(): String = "Профессия должна начинаться со строчной буквы"
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun isValidAnswer(answer: String): Boolean = !answer.matches(Regex(".*\\d.*"))
            override fun getErrorMessage(): String = "Материал не должен содержать цифр"
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun isValidAnswer(answer: String): Boolean = answer.isDigitsOnly()
            override fun getErrorMessage(): String =
                "Год моего рождения должен содержать только цифры"
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun isValidAnswer(answer: String): Boolean =
                answer.isDigitsOnly() && answer.length == 7

            override fun getErrorMessage(): String = "Серийный номер содержит только цифры, и их 7"
        },
        IDLE("На этом всё, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun isValidAnswer(answer: String): Boolean = true
            override fun getErrorMessage(): String = ""
        };

        abstract fun nextQuestion(): Question

        abstract fun isValidAnswer(answer: String): Boolean

        abstract fun getErrorMessage(): String

    }


}