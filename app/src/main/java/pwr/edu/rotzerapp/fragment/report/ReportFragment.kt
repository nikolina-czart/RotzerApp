package pwr.edu.rotzerapp.fragment.report

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.database.repository.FirebaseRepository
import pwr.edu.rotzerapp.databinding.FragmentChartBinding
import pwr.edu.rotzerapp.databinding.FragmentReportBinding
import pwr.edu.rotzerapp.fragment.chart.ChartViewModel

class ReportFragment: Fragment() {
    private val chartViewModel by viewModels<ReportViewModel>()
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { FirebaseRepository.auth }

    companion object{
        private const val REPORT_DEBUG = "REPORT_FRAGMENT_DEBUG"
        private lateinit var ACTIVITY: MainActivity
    }
}