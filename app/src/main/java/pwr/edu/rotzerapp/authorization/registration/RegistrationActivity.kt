package pwr.edu.rotzerapp.authorization.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.database.dto.MainUser

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {
    private val REGISTRATION_DEBUG = "REGISTRATION_ACTIVITY_DEBUG"

    private val registrationVm: RegistrationViewModel by viewModels<RegistrationViewModel>()
    private val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var loginTextView: TextView? = null
    private var signUpButton: Button? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var usernameEditText: EditText? = null
    private var userAgeEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()

        loginTextView?.setOnClickListener(this)
        signUpButton?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.textGoToLogin -> setupLoginInClick()
            R.id.cirLoginButton -> setupSignUpClick()
        }
    }

    private fun setupLoginInClick() {
//        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun setupSignUpClick() {
        // elvis operator
        val email: String = emailEditText!!.text?.trim().toString()
        val name: String = usernameEditText!!.text?.trim().toString()
        val password: String = passwordEditText!!.text?.trim().toString()
        val age: String = userAgeEditText!!.text?.trim().toString()

        fun isEmpty(checkFiled: String, titleFiled:String): Boolean{
            if(checkFiled.isNotEmpty()){
                return true
            }
            Toast.makeText(applicationContext, "Wprowadź $titleFiled", Toast.LENGTH_SHORT).show()
            return false
        }

        if ( isEmpty(name, "imię") && isEmpty(email, "email") && isEmpty(password, "hasło") && isEmpty(
                age, "wiek")) {
            fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authRes ->

                    if (authRes.user != null) {
                        val user = MainUser(
                            authRes.user!!.uid,
                            name,
                            surname = null,
                            email,
                            partnerID = null,
                            birthDate = null,
                            age.toInt(),
                            pregnant = false,
                            contraception = false
                        )

                        registrationVm.createNewMainUser(user)
                        val intent: Intent =
                            Intent(applicationContext, MainActivity::class.java).apply {
                                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            }
                        startActivity(intent)
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, "Something went wrong1", Toast.LENGTH_SHORT).show()
                    Log.d(REGISTRATION_DEBUG, exception.message.toString())
                }
        } else {
            Toast.makeText(applicationContext, "Something went wrong3", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        loginTextView = findViewById(R.id.textGoToLogin)
        signUpButton = findViewById(R.id.cirLoginButton)
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        usernameEditText = findViewById(R.id.editTextName)
        userAgeEditText = findViewById(R.id.editTextAge);
    }

}

