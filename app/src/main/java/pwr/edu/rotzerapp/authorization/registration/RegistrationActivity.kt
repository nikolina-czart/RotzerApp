package pwr.edu.rotzerapp.authorization.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.authorization.login.LoginActivity
import pwr.edu.rotzerapp.database.dto.MainUserDto

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {
    private val registrationVm: RegistrationViewModel by viewModels<RegistrationViewModel>()
    private val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var loginTextView: TextView? = null
    private var signUpButton: Button? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var usernameEditText: EditText? = null
    private var passwordRepeatEditText: EditText? = null
    private var textInputLayoutName: TextInputLayout? = null
    private var textInputLayoutEmail: TextInputLayout? = null
    private var textInputLayoutPassword: TextInputLayout? = null
    private var textInputLayoutPasswordRepeat: TextInputLayout? = null

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
            R.id.btn_register -> setupSignUpClick()
        }
    }

    private fun setupLoginInClick() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun setupSignUpClick() {
        val email: String = emailEditText!!.text?.trim().toString()
        val name: String = usernameEditText!!.text?.trim().toString()
        val password: String = passwordEditText!!.text?.trim().toString()
        val passwordRepeat: String = passwordRepeatEditText!!.text?.trim().toString()

        val nameValid = validName(name, textInputLayoutName)
        val emailValid = validEmail(email, textInputLayoutEmail)
        val passwordValid = validPassword(password, textInputLayoutPassword)
        val passwordValidRepeat = validPasswordRepeat(password, passwordRepeat, textInputLayoutPasswordRepeat)

        if (nameValid && emailValid && passwordValid && passwordValidRepeat) {
            fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authRes ->
                    if (authRes.user != null) {
                        val user = MainUserDto(
                            authRes.user!!.uid,
                            name,
                            email,
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
                    Toast.makeText(applicationContext, exception.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun validName(checkFiled: String, textInputLayout: TextInputLayout?): Boolean {
        if(!isEmpty(checkFiled)) {
            changeErrorTextAttributes(textInputLayout, "Imię nie może być puste.", true)
            return false
        }
        textInputLayout?.isErrorEnabled = false
        return true
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

    private fun validPasswordRepeat(password: String, passwordRepeat: String, textInputLayout: TextInputLayout?): Boolean {
        if(!isEmpty(passwordRepeat)) {
            changeErrorTextAttributes(textInputLayout, "Hasło nie może być puste.", true)
            return false
        }else if(password != passwordRepeat) {
            changeErrorTextAttributes(textInputLayout, "Hasła się różnią między sobą", true)
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
        loginTextView = findViewById(R.id.textGoToLogin)
        signUpButton = findViewById(R.id.btn_register)
        emailEditText = findViewById(R.id.et_register_email)
        passwordEditText = findViewById(R.id.et_register_password)
        usernameEditText = findViewById(R.id.et_register_name)
        passwordRepeatEditText = findViewById(R.id.et_register_password_repeat)
        textInputLayoutName = findViewById(R.id.tl_register_name)
        textInputLayoutEmail = findViewById(R.id.tl_register_email)
        textInputLayoutPassword = findViewById(R.id.tl_register_password)
        textInputLayoutPasswordRepeat = findViewById(R.id.tl_register_password_repeat)
    }

}

