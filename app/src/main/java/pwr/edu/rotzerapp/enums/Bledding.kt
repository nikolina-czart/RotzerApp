package pwr.edu.rotzerapp.enums

enum class Bleeding(val describe: String, val increasedBleeding: String) {
    NO_BLEEDING("No bleeding", "0"),
    LITTLE_BLEEDING("Little bleeding", "25"),
    MEDIUM_BLEEDING("Medium bleeding", "50"),
    HEAVY_BLEEDING("Heavy bleeding", "75"),
    VERY_HEAVY_BLEEDING("Very heavy bleeding", "100")
}