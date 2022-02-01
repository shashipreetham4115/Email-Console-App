package entites

import enums.ROLE
import java.time.LocalDate
import java.util.*

data class User(
    var firstName: String,
    var lastName: String,
    val email: String,
    var mobileNumber: Long,
    var password: String,
    var securityQues: Int,
    var securityAns: String,
    var role: Role = Role(ROLE.USER),
    val id: String = UUID.randomUUID().toString(),
    var dob: LocalDate? = null,
    val createdDate: LocalDate = LocalDate.now(),
    val activityLog: MutableList<Activity> = mutableListOf(),
    val groups: MutableList<String> = mutableListOf(),
)