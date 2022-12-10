package pwr.edu.rotzerapp.fragment.today

import androidx.lifecycle.ViewModel
import pwr.edu.rotzerapp.database.repository.FirebaseRepository

class TodayViewModel(selectDate: String): ViewModel() {
    private val repository = FirebaseRepository()

    val symptomByDay = repository.getSymptomsByDay(selectDate)
}