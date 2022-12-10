package pwr.edu.rotzerapp.database.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pwr.edu.rotzerapp.database.dto.*

class FirebaseRepository {
    private val FIREBASE_DEBUG = "FIREBASE_DEBUG"
    private val db = Firebase.firestore
    private val cloud = FirebaseFirestore.getInstance()

    companion object FirebaseManagerAuth {
        val auth = FirebaseAuth.getInstance()
        fun getCurrentUserID(): String? = FirebaseAuth.getInstance().currentUser?.uid
    }

    fun createNewUser(user: MainUserDto) {
        user.uid?.let {
            db.collection("users").document(it)
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
            db.collection("users")
                .document(it)
                .collection("symptoms")
                .document(day)
                .set(temperatureDto, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener {
                        e -> Log.w(FIREBASE_DEBUG, "Error writing document", e)
                }
        }
    }

    fun saveDayMucus(day: String, mucusDto: MucusDto) {
        getCurrentUserID()?.let {
            db.collection("users")
                .document(it)
                .collection("symptoms")
                .document(day)
                .set(mucusDto, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener {
                        e -> Log.w(FIREBASE_DEBUG, "Error writing document", e)
                }
        }
    }

    fun saveDayCervix(day: String, cervixDto: CervixDto) {
        getCurrentUserID()?.let {
            db.collection("users")
                .document(it)
                .collection("symptoms")
                .document(day)
                .set(cervixDto, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener {
                        e -> Log.w(FIREBASE_DEBUG, "Error writing document", e)
                }
        }
    }

    fun saveDayBleeding(day: String, bleedingDto: BleedingDto) {
        getCurrentUserID()?.let {
            db.collection("users")
                .document(it)
                .collection("symptoms")
                .document(day)
                .set(bleedingDto, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(
                        FIREBASE_DEBUG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener {
                        e -> Log.w(FIREBASE_DEBUG, "Error writing document", e)
                }
        }
    }
}