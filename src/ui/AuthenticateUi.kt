package ui

import entites.Profile
import entites.User
import logic.auth.AuthenticateHandler
import ui.services.AuthenticateUiServices
import ui.services.ToDoMenuServices
import ui.utils.InputUtil
import ui.utils.InputValidationUtil

object AuthenticateUi : ToDoMenuServices, AuthenticateUiServices {
    private val auth = AuthenticateHandler()
    private val questions = arrayOf(
        "What city were you born in?",
        "What is your oldest sibling’s middle name?",
        "What was the first concert you attended?",
        "What was the make and model of your first car?",
        "In what city or town did your parents meet?",
        "What is the name of your favorite childhood friend?",
        "What was your childhood nickname?",
        "What school did you attend for sixth grade?",
        "What was your favorite place to visit as a child?",
        "What is the country of your ultimate dream vacation?"
    )

    //    var loggedInUser: Profile? = Profile("Shashi Preetham", "shashipreetham@zoho.com", ROLE.USER)
//        private set
    var loggedInUser: Profile? = null
        private set

    override fun toDoMenu() {
        val request = """
    --------Welcome to Zoho Mail---------
    
    1) Sign in
    2) Sign up
    3) Forgot Password
    4) Exit
    Please Enter Your Choice
    """.trimIndent()
        while (loggedInUser == null) {
            when (InputUtil.getInt(request)) {
                1 -> signIn()
                2 -> signUp()
                3 -> forgotPassword()
                4 -> break
                else -> println("Please Enter Valid Option")
            }
        }
    }

    override fun signIn() {
        val email = InputValidationUtil.getEmail("Please Enter Your Email ID")
        if (email == "--q") return
        val password = InputUtil.getString("Please Enter Your Password")
        if (password == "--q") return
//      val password = InputUtil.getPassword()
        loggedInUser = auth.signIn(email, password)
        if (loggedInUser == null) {
            println("Please Enter Valid Email ID or Password")
            toDoMenu()
        }
    }

    override fun signOut() {
        loggedInUser = null
    }

    override fun signUp() {
        var email = InputValidationUtil.getEmail("Please Enter Your Username(email : username@zoho.com)")
        if (email == "--q") return
        var isEmail = auth.isEmailAvailable(email)
        while (isEmail != "Email Available") {
            if (isEmail == "Invalid Domain") println("Sorry, only letters (a-z), numbers (0-9), and periods (.) are allowed.")
            else println(isEmail)
            email = InputValidationUtil.getEmail("Please Enter Your Username(email : username@zoho.com)")
            if (email == "--q") return
            isEmail = auth.isEmailAvailable(email)
        }
        val password = InputUtil.getString("Please Enter Your Password")
        if (password == "--q") return
        val firstName = InputUtil.getString("Please Enter Your First Name")
        if (firstName == "--q") return
        val lastName = InputUtil.getString("Please Enter Your Last Name")
        if (lastName == "--q") return
        val mobileNumber = InputUtil.getLong("Please Enter Your Number")
        if (mobileNumber == -1L) return
        val (securityQues, securityAns) = passwordRecovery().split("||")
        if (securityQues == "--q") return
        val user = User(firstName, lastName, email, mobileNumber, password, securityQues.toInt(), securityAns)
        loggedInUser = auth.signUp(user)
    }

    override fun forgotPassword() {
        val email = doEmailExists()
        if (email == "false") return
        val questionNo = auth.getSecurityQuestion(email)
        var securityAns = InputUtil.getString(questions[questionNo])
        if (securityAns == "--q") return
        while (!auth.forgotPassword(email, securityAns)) {
            println("Please Enter Valid Answer")
            securityAns = InputUtil.getString(questions[auth.getSecurityQuestion(email)])
            if (securityAns == "--q") return
        }
        val password = InputUtil.getString("Please Enter Your New Password")
        if (password == "--q") return
        auth.setPassword(email, password)
        println("New Password For $email Set Succesfully")
    }

    private fun doEmailExists(): String {
        var email = InputValidationUtil.getEmail("Please Enter Your Email ID")
        if (email == "--q") return "false"
        while (auth.isEmailAvailable(email) != "Email Already Exists") {
            println("Couldn’t find your Account")
            email = InputValidationUtil.getEmail("Please Enter Your Email ID")
            if (email == "--q") return "false"
        }
        return email
    }

    private fun passwordRecovery(): String {
        var request = "Password Recovery Security Questions\n\n"
        for (i in questions.indices) request += "${i + 1}) ${questions[i]}\n"
        request += "\nPlease Choose Your Security Question"
        var securityAnswer = ""
        while (securityAnswer == "") {
            val choice = InputUtil.getInt(request)
            if (choice == -1) return "--q||--q"
            when (choice) {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> {
                    while (securityAnswer == "") {
                        val answer = InputUtil.getString(questions[choice - 1])
                        if (answer == "--q") return "--q||--q"
                        if (answer != "")
                            securityAnswer = "${choice - 1}||$answer"
                        else println("Please Enter Valid Answer")
                    }
                }
                else -> println("Please Choose Valid Option")
            }
        }
        return securityAnswer
    }
}