package pwr.edu.rotzerapp.fragment.chart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.database.repository.FirebaseRepository
import pwr.edu.rotzerapp.databinding.FragmentChartBinding
import pwr.edu.rotzerapp.fragment.report.ReportFragment

class ChartFragment: Fragment() {
    private val chartViewModel by viewModels<ChartViewModel>()
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { FirebaseRepository.auth }

    companion object{
        private const val CHART_DEBUG = "CHART_FRAGMENT_DEBUG"
        private lateinit var ACTIVITY: MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as MainActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}