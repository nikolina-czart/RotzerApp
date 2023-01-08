package pwr.edu.rotzerapp.enums

enum class MucusType(val describe: String, val short:String, val type:Int) {

    NOTHING("nic", "∅",0),
    DRY("suchość","su",0),
    CONSTANS ("stała wydzielina","c",0),
    OBSERVATION ("wnikliwa obserwacja","w",1),
    MOISTURE ("wilgotność na zewnątrz","wl",1),
    MUCUS ("śluz","S",1),

    WHITE ("biały,","b",1),
    YELLOW ("żółty,","ż",1),
    YELLOWISH("żółtawy","żł",1),
    LUMPY ("grudkowaty","gr",1),
    CLOUDY ("mętny","m",1),
    STICKY ("kleisty","kl",1),

    EGG ("białko jaja","Bj",2),
    GLASSY ("szklisty","szk",2),
    LIQUID ("płynny", "pł",2),
    WET ("mokro-ślisko", "mś",2);

    companion object {
        fun findByDescription(describe: String): MucusType {
            return values().first { it.describe === describe }
        }
    }


}