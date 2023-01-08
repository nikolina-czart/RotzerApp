package pwr.edu.rotzerapp.database.dto

import com.google.firebase.Timestamp
import pwr.edu.rotzerapp.enums.Bleeding
import java.time.LocalDate

data class BleedingDto(
    val increasedBleeding: Bleeding,
    var date: Timestamp = Timestamp.now()
)