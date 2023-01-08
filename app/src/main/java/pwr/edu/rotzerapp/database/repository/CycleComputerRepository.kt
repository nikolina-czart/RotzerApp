package pwr.edu.rotzerapp.database.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import pwr.edu.rotzerapp.CycleInfoParameters
import pwr.edu.rotzerapp.database.dto.Symptom
import pwr.edu.rotzerapp.enums.Bleeding
import java.time.ZoneId
import kotlin.math.abs


//Klasa do analizy skończonych cykli

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

    fun listTemperatures(currentUserID: String): List<CycleInfoParameters> {
        val list = this.getCyclesList(currentUserID)
        list.forEach{
            val maxValue = it.symptoms.reversed().maxOf { d -> d.vaginalMucus?.type ?:0 }
            val symptom = it.symptoms.reversed().first { d-> d.vaginalMucus?.type ?:0 == maxValue }

            it.ovulation = symptom.date
            var ovulationIndex = it.symptoms.indexOf(symptom)
            var firstHigh: Int? = null

            var firstIndex = ovulationIndex - 6
            var max = 0.0

            if(firstIndex < 0) {
                firstIndex = 0
            }

            while (true) {
                val tmpTemperature = it.symptoms.subList(firstIndex, ovulationIndex);
                max = tmpTemperature.maxOf { s -> s.temperature?.toDouble()?:0.0 }

                if(it.symptoms.size <= ovulationIndex + 1) {
                    break
                }

                if((it.symptoms[ovulationIndex + 1].temperature?.toDouble() ?: 0.0) > max) {
                    firstHigh = ovulationIndex + 1
                    it.sixTemperatures = tmpTemperature.map { k->k.date }.toList()
                    break
                }

                firstIndex++
                ovulationIndex++
            }

            if(firstIndex + 2 < it.symptoms.size) {
                var math = abs(it.symptoms[firstIndex + 2].temperature?.toDouble() ?: (0.0 - max))
                if(math >= 0.2) {
                    it.higherTemperature = it.symptoms.subList(ovulationIndex, ovulationIndex + 3).map { d->d.date }
                } else {
                    it.higherTemperature = it.symptoms.subList(ovulationIndex, ovulationIndex + 4).map { d->d.date }
                }

                it.youCanSexToday = it.higherTemperature.last()
            }

        }
        return list //lista cykli z obliczonymi wszystkimi parametrami, jeśli jest taka możliwość
    }






}