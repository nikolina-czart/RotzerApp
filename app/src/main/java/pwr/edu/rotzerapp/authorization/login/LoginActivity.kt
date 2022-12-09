package pwr.edu.rotzerapp.authorization.login

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.authorization.registration.RegistrationActivity
import pwr.edu.rotzerapp.database.repository.FirebaseRepository

class LoginActivity() : AppCompatActivity(), View.OnClickListener, Parcelable {
    private val fbAuth = FirebaseAuth.getInstance()

    private var registerTextView: TextView? = null
    private var signInButton: Button? = null
    private var loginEditText: TextInputEditText? = null
    private var passwordEditText: TextInputEditText? = null
    private var textInputLayoutEmail: TextInputLayout? = null
    private var textInputLayoutPassword: TextInputLayout? = null

    constructor(
        parcel: Parcel) : this()

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
            R.id.btn_login -> setupLoginClick()
        }
    }

    private fun setupRegistrationClick() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun setupLoginClick() {
        val email = loginEditText!!.text?.trim().toString()
        val password = passwordEditText!!.text?.trim().toString()
        val emailValid = validEmail(email, textInputLayoutEmail)
        val passwordValid = validPassword(password, textInputLayoutPassword)


        if (emailValid && passwordValid) {
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
                    changeErrorTextAttributes(textInputLayoutEmail, "", false)
                    changeErrorTextAttributes(textInputLayoutPassword, "", false)
                }
                .addOnFailureListener {exception ->
                    Toast.makeText(applicationContext, exception.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun validEmail(checkFiled: String, textInputLayout: TextInputLayout?): Boolean {
        if(!isEmpty(checkFiled)) {
            changeErrorTextAttributes(textInputLayout, "E-mail nie może być pusty.", true)
            return false
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(checkFiled).matches()) {
            changeErrorTextAttributes(textInputLayout, "Zły format e-mailu.", true)
            return false
        }
        textInputLayout?.isErrorEnabled = false
        return true
    }

    private fun validPassword(checkFiled: String, textInputLayout: TextInputLayout?): Boolean {
        if(!isEmpty(checkFiled)) {
            changeErrorTextAttributes(textInputLayout, "Hasło nie może być puste.", true)
            return false
        }else if(checkFiled.length < 6) {
            changeErrorTextAttributes(textInputLayout, "Hasło musi składać się z minimum 6 znaków.", true)
            return false
        }
        textInputLayout?.isErrorEnabled = false
        return true
    }

    private fun changeErrorTextAttributes(textInputLayout: TextInputLayout?, massage: String, enabled: Boolean) {
        textInputLayout?.isErrorEnabled = enabled
        textInputLayout?.error = massage
    }

    private fun isEmpty(checkFiled: String): Boolean {
        if (checkFiled.isNotEmpty()) {
            return true
        }
        return false
    }


    private fun init() {
        registerTextView = findViewById(R.id.textSingUp)
        signInButton = findViewById(R.id.btn_login)
        loginEditText = findViewById(R.id.et_login_email)
        passwordEditText = findViewById(R.id.et_login_password)
        textInputLayoutEmail = findViewById(R.id.tl_login_email)
        textInputLayoutPassword = findViewById(R.id.tl_login_password)
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