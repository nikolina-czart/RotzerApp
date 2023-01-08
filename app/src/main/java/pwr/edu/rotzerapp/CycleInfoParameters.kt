package pwr.edu.rotzerapp

import pwr.edu.rotzerapp.database.dto.Symptom
import java.time.LocalDate

class CycleInfoParameters {
    var dateStart: LocalDate = LocalDate.now()
    var symptoms: MutableList<Symptom> = mutableListOf()
    var ovulation: LocalDate? = null
    var sixTemperatures: List<LocalDate> = listOf()
    var higherTemperature: List<LocalDate> = listOf()
    var youCanSexToday: LocalDate? = null
}
