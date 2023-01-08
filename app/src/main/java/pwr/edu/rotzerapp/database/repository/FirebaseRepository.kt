package pwr.edu.rotzerapp.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import pwr.edu.rotzerapp.database.dto.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FirebaseRepository: Repository() {
    private val USERS = "users"
    private val SYMPTOMS = "symptoms"
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    companion object FirebaseManagerAuth {
        val auth = FirebaseAuth.getInstance()
        fun getCurrentUserID(): String? = FirebaseAuth.getInstance().currentUser?.uid
    }

    fun createNewUser(user: MainUserDto) {
        user.uid?.let {
            db.collection(USERS).document(it)
                .set(user)
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e -> Log.w(FIREBASE_DEBUG, "Error writing document", e) }
        }
    }

    fun saveDayTemperature(day: String, temperatureDto: TemperatureDto) {
        val zdt: ZonedDateTime = ZonedDateTime.of(LocalDate.parse(day, formatter).atStartOfDay(), ZoneId.systemDefault())
        temperatureDto.date = Timestamp(zdt.toInstant().toEpochMilli() / 1000, 0)

//            .from()

        getCurrentUserID()?.let {
            db.collection(USERS)
                .document(it)
                .collection(SYMPTOMS)
                .document(day)
                .set(temperatureDto, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(FIREBASE_DEBUG, "Error writing document", e)
                }
        }
    }


    fun saveDayMucus(day: String, mucusDto: MucusDto) {
        val zdt: ZonedDateTime = ZonedDateTime.of(LocalDate.parse(day, formatter).atStartOfDay(), ZoneId.systemDefault())
        mucusDto.date = Timestamp(zdt.toInstant().toEpochMilli() / 1000, 0)

        getCurrentUserID()?.let {
            db.collection(USERS)
                .document(it)
                .collection(SYMPTOMS)
                .document(day)
                .set(mucusDto, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(FIREBASE_DEBUG, "Error writing document", e)
                }
        }
    }

    fun saveDayCervix(day: String, cervixDto: CervixDto) {
        val zdt: ZonedDateTime = ZonedDateTime.of(LocalDate.parse(day, formatter).atStartOfDay(), ZoneId.systemDefault())
        cervixDto.date = Timestamp(zdt.toInstant().toEpochMilli() / 1000, 0)

        getCurrentUserID()?.let {
            db.collection(USERS)
                .document(it)
                .collection(SYMPTOMS)
                .document(day)
                .set(cervixDto, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(FIREBASE_DEBUG, "Error writing document", e)
                }
        }
    }

    fun saveDayBleeding(day: String, bleedingDto: BleedingDto) {
        val zdt: ZonedDateTime = ZonedDateTime.of(LocalDate.parse(day, formatter).atStartOfDay(), ZoneId.systemDefault())
        bleedingDto.date = Timestamp(zdt.toInstant().toEpochMilli() / 1000, 0)

        getCurrentUserID()?.let {
            db.collection(USERS)
                .document(it)
                .collection(SYMPTOMS)
                .document(day)
                .set(bleedingDto, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(FIREBASE_DEBUG, "Error writing document", e)
                }
        }
    }

    fun getSymptomsByDay(day: String): LiveData<Symptom> {
        val symptoms = MutableLiveData<Symptom>()
        val uid = auth.currentUser?.uid

        db.collection(USERS)
            .document(uid!!)
            .collection(SYMPTOMS)
            .document(day)
            .get()
            .addOnSuccessListener {
                symptoms.postValue(it.toObject(Symptom::class.java))
                Log.d(FIREBASE_DEBUG, it.data.toString())
            }
            .addOnFailureListener { e ->
                Log.w(FIREBASE_DEBUG, "Error writing document", e)
            }

        return symptoms;
    }

    fun getSymptoms(date: String, lambda: ((Symptom?) -> Unit)) {
        getCurrentUserID()?.let {
            db.collection(USERS)
                .document(it)
                .collection(SYMPTOMS)
                .document(date)
                .get()
                .addOnSuccessListener { doc ->
                    lambda.invoke(doc.toObject(Symptom::class.java))
                }
        }
    }

}