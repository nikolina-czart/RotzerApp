package pwr.edu.rotzerapp.database.dto

import com.google.firebase.Timestamp
import pwr.edu.rotzerapp.enums.CervixHardnessType
import pwr.edu.rotzerapp.enums.CervixHeightType
import pwr.edu.rotzerapp.enums.CervixOpenType
import java.time.LocalDate

data class CervixDto(
    val height: CervixHeightType,
    val dilation: CervixOpenType,
    val hardness: CervixHardnessType,
    var date: Timestamp = Timestamp.now()
)