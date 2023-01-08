package pwr.edu.rotzerapp.database.dto

import com.google.firebase.Timestamp
import java.time.LocalDate

data class TemperatureDto(
    val temperature: String? = "",
    var date: Timestamp = Timestamp.now()
)
