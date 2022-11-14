package pwr.edu.rotzerapp.authorization.registration

import androidx.lifecycle.ViewModel
import pwr.edu.rotzerapp.database.dto.MainUser
import pwr.edu.rotzerapp.database.repository.FirebaseRepository

class RegistrationViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    fun createNewMainUser(user: MainUser){
        repository.createNewUser(user)
    }
}