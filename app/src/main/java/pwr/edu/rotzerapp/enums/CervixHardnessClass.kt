package pwr.edu.rotzerapp.enums

enum class CervixHardnessType(describe: String, type:String) {
    HARD ("twarda","t"),
    MEDIUM("średnio","t/m"),
    SOFT("miękka","m");

    private val describe: String
        get() {
            TODO()
        }

    override fun toString(): String {
        return describe
    }
}