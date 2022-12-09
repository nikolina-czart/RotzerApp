package pwr.edu.rotzerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
//import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import pwr.edu.rotzerapp.databinding.ActivityMainBinding
import pwr.edu.rotzerapp.fragments.ChartFragment
import pwr.edu.rotzerapp.fragments.MenuFragment
import pwr.edu.rotzerapp.fragments.ReportsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener {
//                // ...
//            }
        val chipNavigationBar = findViewById<ChipNavigationBar>(R.id.chipNavigationBar)
        chipNavigationBar.setItemSelected(R.id.today)

        chipNavigationBar.setOnItemSelectedListener {
            var fragment: Fragment = MenuFragment()
            when(it) {
                R.id.like -> {
                    fragment = ReportsFragment()
                }
                R.id.today -> {
                    fragment = MenuFragment()
                }
                R.id.chart -> {
                    fragment = ChartFragment()
                }
            }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit()
        }
    }



}