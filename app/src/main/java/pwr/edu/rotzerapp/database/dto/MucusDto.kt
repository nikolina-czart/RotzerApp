package pwr.edu.rotzerapp.database.dto

import com.google.firebase.Timestamp
import pwr.edu.rotzerapp.enums.MucusType
import java.time.LocalDate

data class MucusDto(
    var vaginalMucus: MucusType,
    var date: Timestamp = Timestamp.now()
)