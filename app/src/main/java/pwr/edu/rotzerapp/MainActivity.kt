package pwr.edu.rotzerapp

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pwr.edu.rotzerapp.databinding.ActivityMainBinding
import pwr.edu.rotzerapp.fragment.chart.ChartFragment
import pwr.edu.rotzerapp.fragment.report.ReportFragment
import pwr.edu.rotzerapp.fragment.today.TodayFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        configureBottomNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.settings -> {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Wylogowanie z aplikacji")
                .setMessage("Czy napewno chcesz wylogować się z aplikacji?")
                .setPositiveButton("Tak") { dialog: DialogInterface?, which: Int ->
                    auth.signOut()
                    finish()
                }
                .setNegativeButton("Nie", null)
                .show()
            true
        }
//        android.R.id.home ->{
//            Toast.makeText(this,"Home action",Toast.LENGTH_LONG).show()
//            true
//        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun configureBottomNavigation() {
        val bottomNavigation = findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_report))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_today))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_chart))
        bottomNavigation.show(2, true)

        var fragment: Fragment = TodayFragment()
        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> makeCurrentFragment(ReportFragment())
                2 -> makeCurrentFragment(TodayFragment())
                3 -> makeCurrentFragment(ChartFragment())
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .commit()
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_main, fragment)
            commit()
        }
    }
}