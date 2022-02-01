package entites
import java.util.UUID

data class Domain(
    val name: String,
    val address : String = UUID.randomUUID().toString(),
    val users: MutableMap<String, User> = mutableMapOf(),
    val groups: MutableMap<String, Group> = mutableMapOf(),
    val admins: MutableList<String> = mutableListOf(),
    val superAdmins: MutableList<String> = mutableListOf()
)