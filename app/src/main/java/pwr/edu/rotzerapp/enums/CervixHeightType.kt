package pwr.edu.rotzerapp.enums

enum class CervixHeightType(describe:String,type:String) {

    LOW("nisko","n"),
    MEDIUM("średnio","ś"),
    HEIGHT("wysoko","w"),
    F_HEIGHT("bardzo wysoko", "bw");

    private val describe: String
        get() {
            TODO()
        }

    override fun toString(): String {
        return describe
    }
}