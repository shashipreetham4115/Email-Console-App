package entites

import enums.ROLE

data class Profile(
    val name: String,
    val email: String,
    val role: ROLE,
)