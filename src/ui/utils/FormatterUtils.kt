package ui.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FormatterUtils {
    fun formatDate(date: LocalDateTime): String? {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
    }

    fun formatString(str: String): String {
        var (newStr, formattedStr) = getFormattedStr(str)
        var index = 100
        while (index < newStr.length) {
            if (newStr[index] == ' ') {
                val substr = newStr.substring(index - 100, index)
                if (!substr.contains("\n"))
                    newStr = "${newStr.substring(0, index)}\n${newStr.substring(index + 1)}"
                index = (index - 100) + substr.indexOf("\n")
                index += if (index + 100 < newStr.length) 100 else newStr.length - index - 1
            }
            index++
        }
        return newStr + formattedStr
    }

    private fun getFormattedStr(str: String): List<String> {
        val replyRegex =
            "---- [a-zA-Z]n\\s[0-9]+/02/[0-9]+\\s[0-9]+:([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([+-]?\\d+))?\\s[a-zA-Z]+ [a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]+\\s[a-zA-Z]ro[a-zA-Z]e\\s----".toRegex()
        val forwardRegex = "============ Forwarded\\smessage\\s============".toRegex()

        val replyIndex = replyRegex.find(str)?.range?.first ?: str.length
        val forwardIndex = forwardRegex.find(str)?.range?.first ?: str.length
        val index = if (replyIndex < forwardIndex) replyIndex else forwardIndex
        return if (index == str.length) listOf(str, "") else listOf(str.substring(0 until index), str.substring(index))
    }
}


