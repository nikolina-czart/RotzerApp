package pwr.edu.rotzerapp

import com.google.firebase.Timestamp
import pwr.edu.rotzerapp.database.dto.Symptom
import java.sql.Time
import java.time.LocalDate

class CycleInfoParameters {
    var dateStart: LocalDate = LocalDate.now()
    var symptoms: MutableList<Symptom> = mutableListOf()
    var ovulation: Timestamp? = null
    var sixTemperatures: List<Timestamp> = listOf()
    var higherTemperature: List<Timestamp> = listOf()
    var youCanSexToday: Timestamp? = null
}
