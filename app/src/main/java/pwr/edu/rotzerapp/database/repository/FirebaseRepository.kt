package pwr.edu.rotzerapp.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pwr.edu.rotzerapp.database.dto.*

class FirebaseRepository: Repository() {
    private val USERS = "users"
    private val SYMPTOMS = "symptoms"

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