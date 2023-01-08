package pwr.edu.rotzerapp.enums

enum class CervixHardnessType(val describe: String, val type:String) {
    HARD ("twarda","t"),
    MEDIUM("średnio","t/m"),
    SOFT("miękka","m");


    companion object {
        fun findByDescription(describe: String): CervixHardnessType {
            return CervixHardnessType.values().first { it.describe === describe }
        }
    }
}