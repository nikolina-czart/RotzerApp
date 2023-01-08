package pwr.edu.rotzerapp.database.dto

import com.google.firebase.Timestamp
import pwr.edu.rotzerapp.enums.*
import java.time.LocalDate

data class Symptom (
    val date: Timestamp = Timestamp.now(),
    val temperature: String? = "",
    val measuringTime: String? = "",
    val vaginalMucus: MucusType? = null,
    val height: CervixHeightType? = null,
    val dilation: CervixOpenType? = null,
    val hardness: CervixHardnessType? = null,
    val increasedBleeding: Bleeding = Bleeding.NO_BLEEDING
)