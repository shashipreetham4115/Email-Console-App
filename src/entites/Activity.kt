package entites

import java.time.LocalDate

data class Activity(
    val loginTime : LocalDate,
    val logoutTime: LocalDate,
    val device : String,
    val duration : Long
)