package pwr.edu.rotzerapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import pwr.edu.rotzerapp.databinding.ActivityMainBinding
import pwr.edu.rotzerapp.fragment.chart.ChartFragment
import pwr.edu.rotzerapp.fragment.report.ReportFragment
import pwr.edu.rotzerapp.fragment.today.TodayFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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