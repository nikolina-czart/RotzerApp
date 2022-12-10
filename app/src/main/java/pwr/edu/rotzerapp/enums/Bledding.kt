package pwr.edu.rotzerapp.enums

import pwr.edu.rotzerapp.R

enum class Bleeding(val describe: String, val increasedBleeding: String, var background: Int) {
    NO_BLEEDING("No bleeding", "0", R.drawable.small_button),
    LITTLE_BLEEDING("Little bleeding", "25", R.drawable.small_button),
    MEDIUM_BLEEDING("Medium bleeding", "50", R.drawable.small_button),
    HEAVY_BLEEDING("Heavy bleeding", "75", R.drawable.small_button),
    VERY_HEAVY_BLEEDING("Very heavy bleeding", "100", R.drawable.small_button);

    fun reset() {
        NO_BLEEDING.background = R.drawable.small_button
        LITTLE_BLEEDING.background = R.drawable.small_button
        MEDIUM_BLEEDING.background = R.drawable.small_button
        HEAVY_BLEEDING.background = R.drawable.small_button
        VERY_HEAVY_BLEEDING.background = R.drawable.small_button
    }
}