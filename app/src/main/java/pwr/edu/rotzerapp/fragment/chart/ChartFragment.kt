package pwr.edu.rotzerapp.fragment.chart

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.database.repository.FirebaseRepository
import pwr.edu.rotzerapp.databinding.FragmentChartBinding

class ChartFragment: Fragment() {
    private val chartViewModel by viewModels<ChartViewModel>()
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { FirebaseRepository.auth }

    companion object{
        private const val CHART_DEBUG = "CHART_FRAGMENT_DEBUG"
        private lateinit var ACTIVITY: MainActivity
    }
}