package ui.utils

import java.util.*

object InputUtil {

    fun getString(request: String): String {
        print("\n$request : ")
        return readln()
    }

    fun getInt(request: String): Int {
        while (true) {
            try {
                return when (val str = getString(request)) {
                    "--q" -> -1
                    else -> str.toInt()
                }
            } catch (e: Exception) {
                println("Please Enter Valid Input")
            }
        }
    }

    fun getLong(request: String): Long {
        while (true) {
            try {
                return when (val str = getString(request)) {
                    "--q" -> -1L
                    else -> str.toLong()
                }
            } catch (e: Exception) {
                println("Please Enter Valid Input")
            }
        }
    }

    fun getMultiLineInput(request: String): String {
        println("\n$request : ")
        var input = ""
        val scanner = Scanner(System.`in`)
        while (true) {
            val i = scanner.nextLine()
            if (i == "--q") {
                return input
            }
            input += "\n" + i
        }
    }

}