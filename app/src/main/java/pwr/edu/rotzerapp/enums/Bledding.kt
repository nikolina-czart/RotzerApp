package pwr.edu.rotzerapp.enums

import pwr.edu.rotzerapp.R

enum class Bleeding(val describe: String, val increasedBleeding: String, var background: Int) {
    NO_BLEEDING("Brak krwawienia", "0", R.drawable.small_button),
    LITTLE_BLEEDING("Małe krwawienie", "25", R.drawable.small_button),
    MEDIUM_BLEEDING("Średnie krwawienie", "50", R.drawable.small_button),
    HEAVY_BLEEDING("Intesywne krwawienie", "75", R.drawable.small_button),
    VERY_HEAVY_BLEEDING("Bardzo intensywne krwawienie", "100", R.drawable.small_button);

    fun reset() {
        NO_BLEEDING.background = R.drawable.small_button
        LITTLE_BLEEDING.background = R.drawable.small_button
        MEDIUM_BLEEDING.background = R.drawable.small_button
        HEAVY_BLEEDING.background = R.drawable.small_button
        VERY_HEAVY_BLEEDING.background = R.drawable.small_button
    }


    companion object {
        fun findByDescription(describe: String): Bleeding {
            return Bleeding.values().first { it.describe === describe }
        }
    }
}