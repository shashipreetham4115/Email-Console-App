package ui.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object FormatterUtils {
    fun formatDate(date: LocalDate): String? {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    fun formatTime(time: LocalTime): String? {
        return time.format(DateTimeFormatter.ofPattern("hh:mm a"))
    }

    fun formatString(str: String): String {
        var newStr: String = str
        var index = 100
        while (index < newStr.length) {
            if (newStr[index] == ' ') {
                val substr = newStr.substring(index - 100, index)
                if (!substr.contains("\n"))
                    newStr = "${newStr.substring(0, index)}\n${newStr.substring(index + 1)}"
                index = (index-100) + substr.indexOf("\n")
                index += if (index + 100 < newStr.length) 100 else newStr.length - index - 1
            }
            index++
        }
        return newStr
    }
}


