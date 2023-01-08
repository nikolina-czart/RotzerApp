package pwr.edu.rotzerapp.enums

enum class CervixOpenType (val describe: String, val type:String) {
        CLOSE("zamknięta", "z"),
        MEDIUM("średnio", "ś"),
        OPEN("otwarta", "o");


    companion object {
        fun findByDescription(describe: String): CervixOpenType {
            return CervixOpenType.values().first { it.describe === describe }
        }
    }
}

