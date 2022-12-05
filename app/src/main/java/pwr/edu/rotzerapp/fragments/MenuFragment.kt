package pwr.edu.rotzerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_menu.*
import pwr.edu.rotzerapp.R
import pwr.edu.rotzerapp.databinding.FragmentMenuBinding
import pwr.edu.rotzerapp.enums.CervixHardnessType
import pwr.edu.rotzerapp.enums.CervixHeightType
import pwr.edu.rotzerapp.enums.CervixOpenType
import pwr.edu.rotzerapp.enums.MucusType


class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding

    private lateinit var mucusSpinner: Spinner
    private lateinit var cervixHardness : Spinner
    private lateinit var cervixHeight : Spinner
    private lateinit var cervixOpen : Spinner

    private lateinit var mucusType: MucusType
    private lateinit var cervixHardnessType: CervixHardnessType
    private lateinit var cervixHeightType: CervixHeightType
    private lateinit var cervixOpenType: CervixOpenType


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveData.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_chartFragment)
        }

        mucusDateToList()
        cervixHardnessDateToList()
        cervixHeightDateToList()
        cervixOpenDateToList()

    }

    private fun mucusDateToList() {

        val mucusT =MucusType.values().toString()


        //typeMucus = mucusT
    }

    private fun cervixHardnessDateToList() {

        val hardnessT = CervixHardnessType.values().toString()
    }

    private fun cervixHeightDateToList() {

        val heightT = CervixHeightType.values().toString()
    }

    private fun cervixOpenDateToList() {

        val openT = CervixOpenType.values().toString()
    }
}
