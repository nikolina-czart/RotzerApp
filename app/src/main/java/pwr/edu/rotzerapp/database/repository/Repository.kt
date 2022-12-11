package pwr.edu.rotzerapp.database.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class Repository {
    protected val FIREBASE_DEBUG = "FIREBASE_DEBUG"
    protected val db = Firebase.firestore
    protected val cloud = FirebaseFirestore.getInstance()
}