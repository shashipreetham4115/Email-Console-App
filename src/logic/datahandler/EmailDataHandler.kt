package logic.datahandler

import entites.Domain
import entites.Role
import entites.User
import enums.ROLE

object EmailDataHandler {
    private val domains = mutableMapOf<String, Domain>()
    private val roles = mutableMapOf<ROLE, Role>()

    init {
        val userRole = Role(ROLE.USER)
        val adminRole = Role(ROLE.ADMIN)
        val sAdminRole = Role(ROLE.SADMIN)
        val domain = Domain("zoho.com")
        addRoles(userRole, adminRole, sAdminRole)
        addDomain(domain)
        val user = User("Shashi", "Pretham", "shashipreetham@zoho.com", 9550113687, "********", 9, "Paris")
        addUser(user, ROLE.SADMIN)
        val user1 = User("Mailer", "Demon", "mailer@zoho.com", 0, "********", 9, "Paris")
        val user2 = User("Welcome", "", "welcome@zoho.com", 2, "********", 9, "Paris")
        val user3 = User("No", "Reply", "no-reply@zoho.com", 2, "********", 9, "Paris")
        val user4 = User("Preetham", "Reddy", "reddy@zoho.com", 2, "********", 9, "Paris")
        addUser(user1, ROLE.SADMIN)
        addUser(user2, ROLE.SADMIN)
        addUser(user3, ROLE.SADMIN)
        addUser(user4, ROLE.SADMIN)
    }

    fun getDomain(id: String): Domain? {
        return domains[id]
    }

    private fun addDomain(domain: Domain) {
        domains[domain.name] = domain
    }

    fun getUser(id: String): User? {
        val (username, domain) = id.split("@")
        return getDomain(domain)?.users?.get(username)
    }

    fun getUserId(email: String): String? {
        return getUser(email)?.id
    }

    fun addUser(user: User, role: ROLE) {
        user.role = roles[role]!!
        val (username, domain) = user.email.split("@")
        getDomain(domain)?.users?.set(username, user)
        MailBoxHandler.createMailBox(user.email)
    }

    private fun addRoles(user: Role, admin: Role, sadmin: Role) {
        admin.userPermissions["adminConsole"] = "Yes"
        sadmin.userPermissions["sadminConsole"] = "Yes"
        roles[ROLE.USER] = user
        roles[ROLE.ADMIN] = admin
        roles[ROLE.SADMIN] = sadmin
    }
}