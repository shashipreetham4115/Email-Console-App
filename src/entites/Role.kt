package entites

import enums.ROLE

data class Role(
    val roleName : ROLE,
    val userPermissions : MutableMap<String,String> = mutableMapOf("incomingEmail" to "Allow","outgoingEmail" to "Allow","loggingIn" to "Allow","adminConsole" to "No","sadminConsole" to "No"),
)