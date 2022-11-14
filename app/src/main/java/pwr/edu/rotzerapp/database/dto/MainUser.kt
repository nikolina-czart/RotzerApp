package pwr.edu.rotzerapp.database.dto

data class MainUser(
    val uid: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val partnerID: String? = null,
    val birthDate: String? = null,
    val age: Int? = null,
    val pregnant: Boolean? = null,
    val contraception: Boolean? = null,
)