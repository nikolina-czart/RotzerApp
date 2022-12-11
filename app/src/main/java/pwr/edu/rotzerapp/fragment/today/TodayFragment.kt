package pwr.edu.rotzerapp.fragment.today

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rezwan.rcalenderlib.callbacks.YearRangeListener
import com.rezwan.rcalenderlib.models.RCalendar
import kotlinx.android.synthetic.main.fragment_today.*
import org.joda.time.LocalDate
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.database.dto.*
import pwr.edu.rotzerapp.database.repository.FirebaseRepository
import pwr.edu.rotzerapp.databinding.FragmentTodayBinding
import pwr.edu.rotzerapp.enums.*

class TodayFragment: Fragment(), YearRangeListener {
    private val repository = FirebaseRepository()
    private var todayVm: TodayViewModel? = null
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { FirebaseRepository.auth }
    private var selectDate: String = ""
    private var bleeding: String = ""
    private val db = Firebase.firestore

    companion object{
        private const val TODAY_DEBUG = "TODAY_FRAGMENT_DEBUG"
        private lateinit var ACTIVITY: MainActivity
        val auth = FirebaseAuth.getInstance()
        fun getCurrentUserID(): String? = FirebaseAuth.getInstance().currentUser?.uid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniSpinner()

        btnPrev.setOnClickListener {
            yearRangeWeekCalendarView.navigateToPrevious()
        }
        btnNext.setOnClickListener {
            yearRangeWeekCalendarView.navigateToNext()
        }

        btnToday.setOnClickListener {
            yearRangeWeekCalendarView.navigateToday()
        }

        btnTemperatureSave.setOnClickListener{
            var temperature = etBodyTemperature!!.text?.trim().toString()

            if(temperature.isNotEmpty() && temperature.toDouble() > 35 && temperature.toDouble() < 39){
                val temperatureDto = TemperatureDto(temperature)
                repository.saveDayTemperature(selectDate, temperatureDto)
            }else {
                Toast.makeText(ACTIVITY, "Temperatura musi mieć zakres 35-39*C", Toast.LENGTH_LONG).show()
            }
        }

        btnMucusSave.setOnClickListener{
            val mucusType = MucusDto(
                spinnerTypeMucus.selectedItem.toString()
            )
            repository.saveDayMucus(selectDate, mucusType)
        }

        btnBleedingSave.setOnClickListener{
            if(bleeding.isNotEmpty() && bleeding.isNotBlank()){
                val bleedingDto = BleedingDto(bleeding)
                repository.saveDayBleeding(selectDate, bleedingDto)
            }else {
                Toast.makeText(ACTIVITY, "Wybierz rodzaj krwawienia", Toast.LENGTH_LONG).show()
            }
        }

        btnCervixSave.setOnClickListener{
            val cervixDto = CervixDto(
                spinnerCervixHeight.selectedItem.toString(),
                spinnerCervixDilation.selectedItem.toString(),
                spinnerCervixHardness.selectedItem.toString()
            )
            repository.saveDayCervix(selectDate, cervixDto)
        }

        btnNoBleeding.setOnClickListener{
            bleeding = Bleeding.NO_BLEEDING.increasedBleeding
            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
        }

        btnLittleBleeding.setOnClickListener{
            bleeding = Bleeding.LITTLE_BLEEDING.increasedBleeding
            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
        }

        btnMediumBleeding.setOnClickListener{
            bleeding = Bleeding.MEDIUM_BLEEDING.increasedBleeding
            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
        }

        btnHeavyBleeding.setOnClickListener{
            bleeding = Bleeding.HEAVY_BLEEDING.increasedBleeding
            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
        }

        btnVeryHeavyBleeding.setOnClickListener{
            bleeding = Bleeding.VERY_HEAVY_BLEEDING.increasedBleeding
            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
        }

        yearRangeWeekCalendarView.setYearRangeListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as MainActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnDateClicked(rCalendar: RCalendar, position: Int) {
        selectDate = "${rCalendar.date.dayOfMonth}-${rCalendar.date.monthOfYear}-${rCalendar.date.year}"
    }

    override fun OnSelectedDateFound(
        rCalendar: RCalendar,
        firstDayOfWeek: String,
        lastDayOfWeek: String,
        dayPosition: Int,
        currentPageNum: Int
    ) {
        yearRangeWeekCalendarView?.post {
            tvChooseDayCalendar.text = "${rCalendar.date.dayOfMonth} ${rCalendar.date.monthOfYear().asText} ${rCalendar.date.year}r"
            selectDate = "${rCalendar.date.dayOfMonth}-${rCalendar.date.monthOfYear}-${rCalendar.date.year}"

            completeExistData()

            btnTemperatureSave
            if(rCalendar.date.isAfter(LocalDate.now())){
                markButtonDisable(btnTemperatureSave)
                markButtonDisable(btnBleedingSave)
                markButtonDisable(btnCervixSave)
                markButtonDisable(btnMucusSave)
                Toast.makeText(ACTIVITY, "Data jeszcze nie wystąpiła", Toast.LENGTH_LONG).show()
            }else{
                markButtonEnable(btnTemperatureSave)
                markButtonEnable(btnBleedingSave)
                markButtonEnable(btnCervixSave)
                markButtonEnable(btnMucusSave)
            }
        }
    }

    private fun completeExistData() {
        val uid = FirebaseRepository.auth.currentUser?.uid

        db.collection("users")
            .document(uid!!)
            .collection("symptoms")
            .document(selectDate)
            .get()
            .addOnSuccessListener {document ->
                if (document != null) {
                    Log.d(TODAY_DEBUG, "DocumentSnapshot data: ${document.data}")
                    val symptom = document.toObject(Symptom::class.java)
                    etBodyTemperature?.setText(symptom?.temperature ?: "")
                    when(symptom?.increasedBleeding){
                        "0" -> {
                            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
                            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            bleeding = symptom?.increasedBleeding
                        }
                        "25" -> {
                            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
                            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            bleeding = symptom?.increasedBleeding
                        }
                        "50" -> {
                            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
                            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            bleeding = symptom?.increasedBleeding
                        }
                        "75" -> {
                            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
                            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            bleeding = symptom?.increasedBleeding
                        }
                        "100" -> {
                            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.border)
                            bleeding = symptom?.increasedBleeding
                        }
                        else -> {
                            bleeding = ""
                            btnNoBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnLittleBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnMediumBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                            btnVeryHeavyBleeding.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
                        }
                    }
                    spinnerCervixHeight.setSelection(getIndex(spinnerCervixHeight, symptom?.height))
                    spinnerCervixDilation.setSelection(getIndex(spinnerCervixDilation, symptom?.dilation))
                    spinnerCervixHardness.setSelection(getIndex(spinnerCervixHardness, symptom?.hardness))
                    spinnerTypeMucus.setSelection(getIndex(spinnerTypeMucus, symptom?.vaginalMucus))
                } else {
                    Log.d(TODAY_DEBUG, "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.w(TODAY_DEBUG, "Error writing document", e)
            }

    }

    private fun iniSpinner() {
        view?.findViewById<Spinner>(R.id.spinnerTypeMucus)?.adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, MucusType.values().map { i -> i.describe})

        view?.findViewById<Spinner>(R.id.spinnerCervixHeight)?.adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, CervixHeightType.values().map { i -> i.describe})

        view?.findViewById<Spinner>(R.id.spinnerCervixDilation)?.adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, CervixOpenType.values().map { i -> i.describe})

        view?.findViewById<Spinner>(R.id.spinnerCervixHardness)?.adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, CervixHardnessType.values().map { i -> i.describe})
    }

    private fun getIndex(spinner: Spinner, myString: String?): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    private fun markButtonDisable(button: Button) {
        button?.isEnabled = false
        button?.setTextColor(ContextCompat.getColor(ACTIVITY, R.color.white))
        button?.setBackgroundColor(ContextCompat.getColor(ACTIVITY, R.color.md_grey_600))
        button?.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
    }

    private fun markButtonEnable(button: Button) {
        button?.isEnabled = true
        button?.background = ContextCompat.getDrawable(ACTIVITY, R.drawable.small_button)
    }
}