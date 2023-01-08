package pwr.edu.rotzerapp.database.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import pwr.edu.rotzerapp.CycleInfoParameters
import pwr.edu.rotzerapp.database.dto.Symptom
import pwr.edu.rotzerapp.enums.Bleeding
import java.time.ZoneId

class CycleComputerRepository: Repository() {
    private val COLLECTION = "users"
    private val COLLECTION_2 = "symptoms"

    private fun getSymptoms(user: String): List<Symptom> {
        var result: List<Symptom>

        runBlocking {
            result = cloud.collection(COLLECTION).document(user)
                .collection(COLLECTION_2).orderBy("date")
                .get().await().toObjects(Symptom::class.java)
        }

        return result;
    }

    private fun getCyclesList(user: String): MutableList<CycleInfoParameters> {
        val list: MutableList<CycleInfoParameters> = mutableListOf()
        val symptoms = this.getSymptoms(user)

        var counter = 0
        var new = true
        for (i in symptoms.indices) {
            if(new) {
                new = false
                list.add(CycleInfoParameters())
                list.get(counter).dateStart = symptoms[i].date.toDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            }

            list.get(counter).symptoms.add(symptoms[i])

            if(i+1 < symptoms.size && symptoms[i + 1].increasedBleeding !== Bleeding.NO_BLEEDING
                && symptoms[i].increasedBleeding === Bleeding.NO_BLEEDING) {
                counter++;
                new = true
            }
        }

        return list;
    }

    fun getComputedCycles(currentUserID: String) {
        val list = this.getCyclesList(currentUserID)
        list.forEach{
            val f = it.symptoms.stream().filter { f -> f.vaginalMucus !== null }.sorted{ o1, o2 -> o2.vaginalMucus!!.type - o1.vaginalMucus!!.type}.findFirst()
            val k = "asd"
        }

    }


}