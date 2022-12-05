package pwr.edu.rotzerapp.database.dto

data class MainUser(
    val uid: String? = "",
    val name: String? = "",
    val surname: String? = "",
    val email: String? = "",
    val partnerID: String? = "",
    val birthDate: String? = "",
    val age: String = "",
    val pregnant: Boolean? = false,
    val contraception: Boolean? = false,
)