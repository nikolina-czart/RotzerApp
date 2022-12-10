package pwr.edu.rotzerapp.fragment.today

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rezwan.rcalenderlib.callbacks.YearRangeListener
import com.rezwan.rcalenderlib.models.RCalendar
import kotlinx.android.synthetic.main.fragment_today.*
import pwr.edu.rotzerapp.MainActivity
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.database.dto.BleedingDto
import pwr.edu.rotzerapp.database.dto.CervixDto
import pwr.edu.rotzerapp.database.dto.MucusDto
import pwr.edu.rotzerapp.database.dto.TemperatureDto
import pwr.edu.rotzerapp.database.repository.FirebaseRepository
import pwr.edu.rotzerapp.databinding.FragmentTodayBinding
import pwr.edu.rotzerapp.enums.*


class TodayFragment: Fragment(), YearRangeListener {
    private val repository = FirebaseRepository()
    private val chartViewModel by viewModels<TodayViewModel>()
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { FirebaseRepository.auth }
    private var selectDate: String = ""
    private var bleeding: String = ""


    companion object{
        private const val TODAY_DEBUG = "TODAY_FRAGMENT_DEBUG"
        private lateinit var ACTIVITY: MainActivity
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
            val temperatureDto = TemperatureDto(
                etBodyTemperature!!.text?.trim().toString(),
                etMeasurementTime!!.text?.trim().toString()
            )
            repository.saveDayTemperature(selectDate, temperatureDto)
        }

        btnMucusSave.setOnClickListener{
            val mucusType = MucusDto(
                spinnerTypeMucus.selectedItem.toString()
            )
            repository.saveDayMucus(selectDate, mucusType)
        }

        btnBleedingSave.setOnClickListener{
            val bleedingDto = BleedingDto(
                bleeding
            )
            repository.saveDayBleeding(selectDate, bleedingDto)
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

            setDailyValue()
        }
    }

    private fun setDailyValue() {
        etBodyTemperature.setText("")
        etMeasurementTime.setText("")
        bleeding = ""
        spinnerCervixHeight.setSelection(0)
        spinnerCervixDilation.setSelection(0)
        spinnerCervixHardness.setSelection(0)
        spinnerTypeMucus.setSelection(0)
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
}