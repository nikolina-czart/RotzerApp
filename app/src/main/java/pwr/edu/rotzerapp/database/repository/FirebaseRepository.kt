package pwr.edu.rotzerapp.database.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pwr.edu.rotzerapp.database.dto.MainUser

class FirebaseRepository {
    private val FIREBASE_DEBUG = "FIREBASE_DEBUG"
    private val db = Firebase.firestore
    private val cloud = FirebaseFirestore.getInstance()

    companion object FirebaseManagerAuth{
        val auth = FirebaseAuth.getInstance()
        fun getCurrentUserID(): String? = FirebaseAuth.getInstance().currentUser?.uid
    }

    fun createNewUser(user: MainUser) {
        cloud.collection("users")
            .document(user.uid!!)
            .set(user)

        Log.d(FIREBASE_DEBUG, user.uid)
    }
}