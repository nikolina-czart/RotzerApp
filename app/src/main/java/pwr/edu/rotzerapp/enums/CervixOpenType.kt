package pwr.edu.rotzerapp.enums

enum class CervixOpenType (describe: String, type:String) {
        CLOSE ("zamknięta","z"),
        MEDIUM("średnio","ś"),
        OPEN("otwarta","o");

        private val describe: String
                get() {
                        TODO()
                }

        override fun toString(): String {
                return describe
        }
}