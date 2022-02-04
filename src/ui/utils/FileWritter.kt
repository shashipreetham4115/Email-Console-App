package ui.utils

import java.io.File

object FileWritter {
    private const val fileName = "hello.txt"
    val myFile = File(fileName)

    private fun writeFile(str: String) {
        myFile.writeText(str)
    }

    private fun getFileData(seperator: String): String {
        var str = mutableListOf<String>()
        myFile.useLines { lines -> lines.forEach { str.add("$it$seperator") } }
        return str.joinToString("")
    }

    fun getFileInput(str: String, seperator: String, title: String): String {
        writeFile(str)
        InputUtil.getString(title)
        val input = getFileData(seperator)
        val reset = "\u001B[0m"
        val green = "\u001B[32m"
        println(green + input + reset)
        return input
    }
}