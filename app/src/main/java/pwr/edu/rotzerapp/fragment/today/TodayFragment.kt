package pwr.edu.rotzerapp.fragment.today

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.database.repository.FirebaseRepository
import pwr.edu.rotzerapp.databinding.FragmentTodayBinding

class TodayFragment: Fragment() {
    private val todayViewModel by viewModels<TodayViewModel>()
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { FirebaseRepository.auth }

    companion object{
        private const val REPORT_DEBUG = "REPORT_FRAGMENT_DEBUG"
        private lateinit var ACTIVITY: MainActivity
    }
}