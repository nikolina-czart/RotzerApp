package pwr.edu.rotzerapp.fragment.chart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_chart.*
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.database.repository.FirebaseRepository
import pwr.edu.rotzerapp.databinding.FragmentChartBinding

import pwr.edu.rotzerapp.fragment.report.ReportFragment

class ChartFragment: Fragment() {
    private var _binding: FragmentChartBinding? = null
    private val db = Firebase.firestore


    companion object {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val uid = FirebaseRepository.auth.currentUser?.uid
        val categories = ArrayList<String>()
        val values = ArrayList<Any>()

        db.collection("users")
            .document(uid!!)
            .collection("symptoms")
            .get().addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document != null && document.data["temperature"] != null) {
                        categories.add(document.id)
                        val temperature = document.data["temperature"].toString()
                        values.add(temperature.toDouble())
                    }
                }

                val categoriesArray: Array<String> = categories.toTypedArray()
                val valuesArray: Array<Any>  = values.toTypedArray()
                val chart =  AAChartModel()
                    .chartType(AAChartType.Line)
                    .title("Wykres metody Rotzera")
                    .backgroundColor(R.color.background_main_color)
                    .dataLabelsEnabled(false)
                    .categories(categoriesArray)
                    .series(arrayOf(
                        AASeriesElement()
                            .data(valuesArray)
                    ))
                chart_view_rotzer_method.aa_drawChartWithChartModel(chart)
            }
            .addOnFailureListener { exception ->
                Log.w(CHART_DEBUG, exception.message, exception)
            }
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