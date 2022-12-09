package pwr.edu.rotzerapp.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.rezwan.rcalenderlib.callbacks.YearRangeListener
import com.rezwan.rcalenderlib.models.RCalendar
import kotlinx.android.synthetic.main.fragment_menu.*
import pwr.edu.rotzerapp.R

import pwr.edu.rotzerapp.enums.CervixHardnessType
import pwr.edu.rotzerapp.enums.CervixHeightType
import pwr.edu.rotzerapp.enums.CervixOpenType
import pwr.edu.rotzerapp.enums.MucusType


class MenuFragment : Fragment(), YearRangeListener {
    private lateinit var binding: pwr.edu.rotzerapp.databinding.FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Spinner
        view.findViewById<Spinner>(R.id.typeMucus).adapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item,MucusType.values().map { i -> i.describe})

        view.findViewById<Spinner>(R.id.heightCervix).adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,CervixHeightType.values().map { i -> i.describe})

        view.findViewById<Spinner>(R.id.openCervix).adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,CervixOpenType.values().map { i -> i.describe})

        view.findViewById<Spinner>(R.id.hardnessCervix).adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,CervixHardnessType.values().map { i -> i.describe})


        yearRangeCalendarView.setYearRangeListener(this)


    }

    override fun OnDateClicked(rCalendar: RCalendar, position: Int) {

    }

    override fun OnSelectedDateFound(
        rCalendar: RCalendar,
        firstDayOfWeek: String,
        lastDayOfWeek: String,
        dayPosition: Int,
        currentPageNum: Int) {

    }




}