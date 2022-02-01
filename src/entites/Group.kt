package entites

import java.util.*

data class Group(
    val id : String = UUID.randomUUID().toString(),
    val name : String,
    val users : MutableList<String> = mutableListOf(),
    val admins : MutableList<String> = mutableListOf(),
    val superAdmins : MutableList<String> = mutableListOf()
)