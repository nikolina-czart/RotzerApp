package pwr.edu.rotzerapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import pwr.edu.rotzerapp.database.repository.FirebaseRepository

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val signOut = findPreference<Preference>("sign_out")
            signOut?.setOnPreferenceClickListener {
                FirebaseRepository.auth.signOut()
                startActivity(Intent(this@SettingsFragment.activity, MainActivity::class.java))
                true
            }

        }
    }
}