package pwr.edu.rotzerapp.enums

enum class CervixHeightType(val describe:String,val type:String) {

    LOW("nisko", "n"),
    MEDIUM("średnio", "ś"),
    HEIGHT("wysoko", "w"),
    F_HEIGHT("bardzo wysoko", "bw");


    companion object {
        fun findByDescription(describe: String): CervixHeightType {
            return CervixHeightType.values().first { it.describe === describe }
        }
    }
}