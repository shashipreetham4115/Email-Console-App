package ui.utils

import java.io.File

object FileWritter {
    private const val fileName = "hello.txt"
    val myFile = File(fileName)

    fun writeFile(str: String) {
        myFile.writeText(str)
    }

    fun getFileData(seperator: String): String {
        var str = mutableListOf<String>()
        myFile.useLines { lines -> lines.forEach { str.add("$it$seperator") } }
        return str.joinToString("")
    }

    fun getFileInput(str: String, seperator: String, title: String): String {
        writeFile(str)
        InputUtil.getString(title)
        return getFileData(seperator)
    }
}