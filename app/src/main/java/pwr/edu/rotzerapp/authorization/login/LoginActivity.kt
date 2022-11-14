package pwr.edu.rotzerapp.authorization.login

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.authorization.registration.RegistrationActivity
import pwr.edu.rotzerapp.database.repository.FirebaseRepository

class LoginActivity() : AppCompatActivity(), View.OnClickListener, Parcelable {
    private val fbAuth = FirebaseAuth.getInstance()

    private val LOGIN_DEBUG = "LOGIN_ACTIVITY_DEBUG"
    private val currentUser = FirebaseRepository.getCurrentUserID()

    private var registerTextView: TextView? = null
    private var signInButton: Button? = null
    private var loginEditText: EditText? = null
    private var passwordEditText: EditText? = null

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (fbAuth.currentUser != null) {
            val intent: Intent = Intent(applicationContext, MainActivity::class.java).apply {
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }

        init()

        registerTextView?.setOnClickListener(this)
        signInButton?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.textSingUp -> setupRegistrationClick()
            R.id.cirLoginButton -> setupLoginClick()
        }
    }

    private fun setupRegistrationClick() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun setupLoginClick() {
        val email = loginEditText!!.text?.trim().toString()
        val password = passwordEditText!!.text?.trim().toString()

        fun isEmpty(checkFiled: String, titleFiled: String): Boolean { // nested functions
            if (checkFiled.isNotEmpty()) {
                return true
            }
            Toast.makeText(applicationContext, "Wprowadź $titleFiled", Toast.LENGTH_SHORT).show()
            return false
        }

        if (isEmpty(email, "email") && isEmpty(password, "hasło")) {
            FirebaseRepository.auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authRes ->
                    if (authRes.user != null) {
                        val intent: Intent =
                            Intent(applicationContext, MainActivity::class.java).apply {
                                flags =
                                    (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            }
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, "Niepoprawne dane", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun init() {
        registerTextView = findViewById(R.id.textSingUp)
        signInButton = findViewById(R.id.cirLoginButton)
        loginEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginActivity> {
        override fun createFromParcel(parcel: Parcel): LoginActivity {
            return LoginActivity(parcel)
        }

        override fun newArray(size: Int): Array<LoginActivity?> {
            return arrayOfNulls(size)
        }
    }
}