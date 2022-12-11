package pwr.edu.rotzerapp.database.dto

import pwr.edu.rotzerapp.enums.*

data class Symptom (
    val temperature: String? = "",
    val measuringTime: String? = "",
    val vaginalMucus: String? = "",
    val height: String? = "",
    val dilation: String? = "",
    val hardness: String? = "",
    val increasedBleeding: String? = ""
)