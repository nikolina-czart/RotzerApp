package pwr.edu.rotzerapp.database.dto

import java.time.LocalDate

data class Cycle(
    val startAt: Long = System.currentTimeMillis(),
    val symptoms: List<Symptom> = ArrayList()
)